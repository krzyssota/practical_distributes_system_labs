package ks406362.dao;

import com.aerospike.client.Record;
import com.aerospike.client.*;
import com.aerospike.client.policy.*;
import com.google.gson.reflect.TypeToken;
import ks406362.UserTag;
import ks406362.UserTags;
import ks406362.avro.SerDe;
import ks406362.domain.Action;
import ks406362.schema.SchemaVersion;
import org.apache.avro.Schema;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.schema.registry.client.SchemaRegistryClient;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;
import org.xerial.snappy.Snappy;

@Component
public class UserTagDao {

    private static final String NAMESPACE = "mimuw";
    private static final String SET = "tags";
    private static final String TAGS_BIN = "tag";
    private static final String VERSION_BIN = "version";
    private static final int MAX_TAGS_LEN = 200;
    private static final Gson GSON = new Gson();

    private AerospikeClient client;
    private SerDe<UserTags> serde;
    private SerDe<UserTag> serdeTag;


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
        this.serde = new SerDe<>(UserTags.getClassSchema());
        this.serdeTag = new SerDe<>(UserTag.getClassSchema());

        Log.setCallbackStandard();
        Log.setLevel(Log.Level.INFO);
    }

   /* @PostConstruct
    public void registerUdfs() throws IOException {
        LuaConfig.SourceDirectory = "src/main/resources/udf/";
        RegisterTask task = client.register(null, "src/main/resources/udf/count.lua", "count.lua", Language.LUA);
        task.waitTillComplete(1000, 10000);
    }*/

    public UserTags debugGet(String cookie) throws IOException {
        Policy readPolicy = new Policy(client.readPolicyDefault);
        Key key = new Key(NAMESPACE, SET, cookie);
        Record record = client.get(readPolicy, key, VERSION_BIN, TAGS_BIN);
        if (record == null) {
            return null;
        }
        return GSON.fromJson((String) record.getValue(TAGS_BIN), UserTags.class);
        //Type userTagsType = new TypeToken<UserTags>() {}.getType();
        //String s = (String) record.getValue(TAGS_BIN);
        //return GSON.fromJson(s, userTagsType);
        //return GSON.fromJson(new String(Snappy.uncompress((byte []) record.getValue(TAGS_BIN)), StandardCharsets.UTF_8), UserTags.class);
    }

    public void debugPut(UserTags tags) throws IOException {
        WritePolicy writePolicy = new WritePolicy(client.writePolicyDefault);
        writePolicy.sendKey = true;
        Key key = new Key(NAMESPACE, SET, String.valueOf(tags.getCookie()));
        Bin versionBin = new Bin(VERSION_BIN, schemaVersion.getCurrentSchemaVersion());
        String s = GSON.toJson(String.valueOf(tags));
        String s2 = removeQuotesAndUnescape(s);
        Log.info("serialized tags unclean: " + s + "\nserialized tags clean: " + s2);
        Bin messageBin = new Bin(TAGS_BIN, s2);
        client.put(writePolicy, key, versionBin, messageBin);
    }

    private String removeQuotesAndUnescape(String uncleanJson) {
        String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
        return StringEscapeUtils.unescapeJava(noQuotes);
    }

    public void put(UserTags tags, WritePolicy writePolicy) throws IOException {
        Key key = new Key(NAMESPACE, SET, String.valueOf(tags.getCookie()));
        Bin versionBin = new Bin(VERSION_BIN, schemaVersion.getCurrentSchemaVersion());
        Bin messageBin = new Bin(TAGS_BIN, serde.serialize(tags));
        client.put(writePolicy, key, versionBin, messageBin);
    }

    public void append(String cookie, Action action, UserTag tag) {
        for (int whichTry = 0; whichTry < 3; whichTry++){
            Policy readPolicy = new Policy(client.readPolicyDefault);
            WritePolicy writePolicy = new WritePolicy(client.writePolicyDefault);
            writePolicy.sendKey = true;
            Key key = new Key(NAMESPACE, SET, String.valueOf(cookie));
            try {
                Record record = client.get(readPolicy, key, VERSION_BIN, TAGS_BIN);
                UserTags userTags = (record == null) ?
                        new UserTags(cookie, new ArrayList<>(), new ArrayList<>()) :
                        deserializeUserTags(record);
                List<UserTag> currTags = (action == Action.BUY) ? userTags.getBuys() : userTags.getViews();

                currTags.add(tag);
                currTags.sort(Comparator.comparing(UserTag::getTime, (t1, t2) -> {
                    Instant d1 = LocalDateTime.parse(t1, DateTimeFormatter.ISO_ZONED_DATE_TIME).toInstant(ZoneOffset.UTC);
                    Instant d2 = LocalDateTime.parse(t2, DateTimeFormatter.ISO_ZONED_DATE_TIME).toInstant(ZoneOffset.UTC);
                    return d2.compareTo(d1);
                }));
                if (currTags.size() == MAX_TAGS_LEN) {
                    currTags.remove(currTags.size() - 1);
                }
                if (action == Action.BUY) {
                    userTags.setBuys(currTags);
                } else {
                    userTags.setBuys(currTags);
                }
                writePolicy.generation = (record != null) ? record.generation : 0;
                writePolicy.generationPolicy = GenerationPolicy.EXPECT_GEN_EQUAL;
                put(userTags, writePolicy);
                return;
            } catch (AerospikeException e) {
                if (e.getResultCode() == ResultCode.GENERATION_ERROR) {
                    Log.warn("GENERATION_ERROR for " + cookie + " " + tag.toString());
                } else {
                    throw e;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private UserTags deserializeUserTags(Record record){
        int writerSchemaId = record.getInt(VERSION_BIN);
        if (schemaVersion.getCurrentSchemaVersion() == writerSchemaId) {
            return serde.deserialize((byte[]) record.getValue(TAGS_BIN), UserTags.getClassSchema());
        }
        String schema = schemaRegistryClient.fetch(writerSchemaId);
        if (schema == null) {
            throw new IllegalStateException("Schema with id: " + writerSchemaId + " does not exist");
        }

        return serde.deserialize((byte[]) record.getValue(TAGS_BIN), new Schema.Parser().parse(schema));
    }



    public UserTags get(String cookie) {
        try {
            Policy readPolicy = new Policy(client.readPolicyDefault);
            Key key = new Key(NAMESPACE, SET, cookie);
            Record record = client.get(readPolicy, key, VERSION_BIN, TAGS_BIN);
            if (record == null) {
                return null;
            }

            int writerSchemaId = record.getInt(VERSION_BIN);
            if (schemaVersion.getCurrentSchemaVersion() == writerSchemaId) {
                return serde.deserialize((byte[]) record.getValue(TAGS_BIN), UserTags.getClassSchema());
            }

            String schema = schemaRegistryClient.fetch(writerSchemaId);
            if (schema == null) {
                throw new IllegalStateException("Schema with id: " + writerSchemaId + " does not exist");
            }

            return serde.deserialize((byte[]) record.getValue(TAGS_BIN), new Schema.Parser().parse(schema));
        } catch (Exception e){
            Log.error("error in get: " + e);
            throw e;
        }
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

   /* public Long count() {
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
*/
    @PreDestroy
    public void close() {
        client.close();
    }
}
