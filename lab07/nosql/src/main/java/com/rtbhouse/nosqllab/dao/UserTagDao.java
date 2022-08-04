package com.rtbhouse.nosqllab.dao;

import java.io.IOException;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.aerospike.client.*;
import com.aerospike.client.Record;
import com.aerospike.client.policy.*;
import org.apache.avro.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.schema.registry.client.SchemaRegistryClient;
import org.springframework.stereotype.Component;

import com.aerospike.client.lua.LuaConfig;
import com.aerospike.client.query.ResultSet;
import com.aerospike.client.query.Statement;
import com.aerospike.client.task.RegisterTask;
import com.rtbhouse.nosqllab.avro.SerDe;
import com.rtbhouse.nosqllab.schema.SchemaVersion;
import com.rtbhouse.nosqllab.UserTag;




@Component
public class UserTagDao {

    private static final String NAMESPACE = "mimuw";
    private static final String SET = "messages";
    private static final String MESSAGE_BIN = "message";
    private static final String VERSION_BIN = "version";

    private AerospikeClient client;
    private SerDe<UserTag> serde;

    @Autowired
    private SchemaVersion schemaVersion;

    @Autowired
    private SchemaRegistryClient schemaRegistryClient;

    private static ClientPolicy defaultClientPolicy() {
        ClientPolicy defaulClientPolicy = new ClientPolicy();
        defaulClientPolicy.readPolicyDefault.replica = Replica.MASTER_PROLES;
        defaulClientPolicy.readPolicyDefault.socketTimeout = 100;
        defaulClientPolicy.readPolicyDefault.totalTimeout = 100;
        defaulClientPolicy.writePolicyDefault.socketTimeout = 15000;
        defaulClientPolicy.writePolicyDefault.totalTimeout = 15000;
        defaulClientPolicy.writePolicyDefault.maxRetries = 3;
        defaulClientPolicy.writePolicyDefault.commitLevel = CommitLevel.COMMIT_MASTER;
        defaulClientPolicy.writePolicyDefault.recordExistsAction = RecordExistsAction.REPLACE;
        return defaulClientPolicy;
    }

    public UserTagDao(@Value("${aerospike.seeds}") String[] aerospikeSeeds, @Value("${aerospike.port}") int port) {
        this.client = new AerospikeClient(defaultClientPolicy(), Arrays.stream(aerospikeSeeds).map(seed -> new Host(seed, port)).toArray(Host[]::new));
        this.serde = new SerDe<>(UserTag.getClassSchema());

        Log.setCallbackStandard();
        Log.setLevel(Log.Level.INFO);
    }

    @PostConstruct
    public void registerUdfs() throws IOException {
        LuaConfig.SourceDirectory = "src/main/resources/udf/";
        RegisterTask task = client.register(null, "src/main/resources/udf/count.lua", "count.lua", Language.LUA);
        task.waitTillComplete(1000, 10000);
    }

    public void put(UserTag tag) {
        WritePolicy writePolicy = new WritePolicy(client.writePolicyDefault);
        writePolicy.sendKey = true;

        Key key = new Key(NAMESPACE, SET, String.valueOf(tag.getCookie()));
        Bin versionBin = new Bin(VERSION_BIN, schemaVersion.getCurrentSchemaVersion());
        Bin messageBin = new Bin(MESSAGE_BIN, serde.serialize(tag));
        client.put(writePolicy, key, versionBin, messageBin);
    }

    public void delete(String cookie){
        WritePolicy writePolicy = new WritePolicy(client.writePolicyDefault);
        Key key = new Key(NAMESPACE, SET, cookie);

        client.delete(writePolicy, key);
    }

    public List<String> getAllKeys(){
            List<String> keys = new ArrayList<>();
            ScanPolicy scanPolicy = new ScanPolicy();
            scanPolicy.includeBinData = false;
            client.scanAll(scanPolicy, NAMESPACE, SET,
                    (key, record) -> {
                        com.aerospike.client.Value userKey = key.userKey;
                        if (userKey != null) {
                            keys.add(key.userKey.toString());
                        }
                    });
            return keys;
    }

    public UserTag get(String cookie) {
        Policy readPolicy = new Policy(client.readPolicyDefault);

        Key key = new Key(NAMESPACE, SET, cookie);
        Record record = client.get(readPolicy, key, VERSION_BIN, MESSAGE_BIN);
        if (record == null) {
            return null;
        }

        int writerSchemaId = record.getInt(VERSION_BIN);
        if (schemaVersion.getCurrentSchemaVersion() == writerSchemaId) {
            return serde.deserialize((byte[]) record.getValue(MESSAGE_BIN), UserTag.getClassSchema());
        }

        String schema = schemaRegistryClient.fetch(writerSchemaId);
        if (schema == null) {
            throw new IllegalStateException("Schema with id: " + writerSchemaId + " does not exist");
        }

        return serde.deserialize((byte[]) record.getValue(MESSAGE_BIN), new Schema.Parser().parse(schema));
    }

    public Long count() {
        Statement statement = new Statement();
        statement.setNamespace(NAMESPACE);
        statement.setSetName(SET);

        try (ResultSet resultSet = client.queryAggregate(null, statement, "count", "count")) {

           Iterator<Object> iterator = resultSet.iterator();

            if (iterator.hasNext()) {
                return (Long) iterator.next();
            }
            return 0L;
        }
    }

    @PreDestroy
    public void close() {
        client.close();
    }
}
