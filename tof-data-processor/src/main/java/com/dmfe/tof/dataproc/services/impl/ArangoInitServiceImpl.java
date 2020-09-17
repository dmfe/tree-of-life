package com.dmfe.tof.dataproc.services.impl;

import com.arangodb.ArangoDB;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.springframework.config.ArangoConfiguration;
import com.dmfe.tof.dataproc.services.api.DbInitService;
import com.dmfe.tof.model.metadata.DbCollectionsUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Log4j2
public class ArangoInitServiceImpl implements DbInitService {
    private ArangoDB arangoDb;
    private String arangoDbName;

    @Autowired
    public ArangoInitServiceImpl(ArangoConfiguration arangoConfig) {
        arangoDb = arangoConfig.arango().build();
        arangoDbName = arangoConfig.database();
    }

    @Override
    public void createCollections() {
        DbCollectionsUtil.getDbCollectionsValues().forEach(dbCollection ->
                DbCollectionsUtil.getCollectionName(dbCollection)
                        .ifPresent(collectionName -> {
                            if (!collectionExists(collectionName)) {
                                log.debug("Creating collection {}", collectionName);

                                arangoDb.db(arangoDbName).createCollection(collectionName);
                                log.info("Collection {} created", collectionName);
                            }
                        })
        );
    }

    private boolean collectionExists(String collectionName) {
        return arangoDb.db(arangoDbName).getCollections().stream()
                .map(CollectionEntity::getName)
                .collect(Collectors.toList()).contains(collectionName);
    }
}
