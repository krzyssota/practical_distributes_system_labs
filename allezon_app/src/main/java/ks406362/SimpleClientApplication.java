package ks406362;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.schema.registry.client.EnableSchemaRegistryClient;

@SpringBootApplication
@EnableSchemaRegistryClient
public class SimpleClientApplication {

    public static final Logger log = LoggerFactory.getLogger(SimpleClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SimpleClientApplication.class, args);
    }

}
