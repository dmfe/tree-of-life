package com.dmfe.tof.dataproc.configs;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.ArangoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableArangoRepositories(basePackages = {"com.dmfe.tof.dataproc.repositories"})
public class ArangoDBConfiguration implements ArangoConfiguration {

    @Value("${arango.hostname}")
    private String arangoHostname;

    @Value("${arango.port}")
    private Integer arangoPort;

    @Value("${arango.db_name}")
    private String arangoDbName;

    @Value("${arango.login}")
    private String arangoLogin;

    @Value("${arango.password}")
    private String arangoPassword;

    @Override
    public ArangoDB.Builder arango() {
        return new ArangoDB.Builder()
                .host(arangoHostname, arangoPort)
                .user(arangoLogin)
                .password(arangoPassword);
    }

    @Override
    public String database() {
        return arangoDbName;
    }
}
