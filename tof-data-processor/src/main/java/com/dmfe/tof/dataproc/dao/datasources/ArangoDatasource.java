package com.dmfe.tof.dataproc.dao.datasources;

import com.arangodb.ArangoDatabase;
import com.arangodb.springframework.config.ArangoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArangoDatasource implements Datasource<ArangoDatabase> {

    private final ArangoDatabase db;

    @Autowired
    public ArangoDatasource(ArangoConfiguration configuration) {
        db = configuration.arango().build()
                .db(configuration.database());
    }

    @Override
    public ArangoDatabase getDb() {
        return db;
    }
}
