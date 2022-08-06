package ks406362.schema;

import ks406362.generated.UserTags;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.schema.registry.SchemaReference;
import org.springframework.cloud.schema.registry.SchemaRegistrationResponse;
import org.springframework.cloud.schema.registry.client.SchemaRegistryClient;
import org.springframework.stereotype.Component;

@Component
public class SchemaVersion implements InitializingBean {

    @Autowired
    private SchemaRegistryClient schemaRegistryClient;

    private SchemaReference currentSchemaReference;

    public int getCurrentSchemaVersion() {
        return currentSchemaReference.getVersion();
    }

    @Override
    public void afterPropertiesSet() {
        SchemaRegistrationResponse register = schemaRegistryClient.register("Message", "avro", UserTags.getClassSchema().toString());
        this.currentSchemaReference = register.getSchemaReference();
    }

}
