package com.dmfe.tof.dataproc.dao.impl;

import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.dmfe.tof.dataproc.components.request.data.RequestProperties;
import com.dmfe.tof.dataproc.dao.api.RwDao;
import com.dmfe.tof.dataproc.dao.converters.TransitionObjectConverter;
import com.dmfe.tof.dataproc.dao.datasources.Datasource;
import com.dmfe.tof.dataproc.dao.dto.TransitionObject;
import com.dmfe.tof.dataproc.dao.queries.Query;
import com.dmfe.tof.dataproc.dao.queries.QueryFactory;
import com.dmfe.tof.dataproc.exceptions.EntityNotFound;
import com.dmfe.tof.dataproc.utils.ArangoHelper;
import com.dmfe.tof.dataproc.utils.Generators;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class ArangoRwDao implements RwDao {

    private final Datasource<ArangoDatabase> datasource;
    private final TransitionObjectConverter<BaseDocument> transitionObjectConverter;

    @Override
    public List<TransitionObject> getEntities(RequestProperties requestProperties) {
        return queryEntities(requestProperties);
    }

    @Override
    public String createEntity(RequestProperties requestProperties) {
        TransitionObject newTransitionObject = requestProperties.getTransitionObject();
        newTransitionObject.setExternalId(Generators.generateRandomeUuid());
        log.debug("Entity to create: \n{}", newTransitionObject);

        getDb().collection(requestProperties.getEntityName())
                .insertDocument(requestProperties.getTransitionObject().toJsonObject());

        return newTransitionObject.getExternalId();
    }

    @Override
    public void replaceEntity(RequestProperties requestProperties) {
        TransitionObject foundObject = getFirstEntity(requestProperties);
        log.debug("Found entity for replace: \n{}", foundObject);

        TransitionObject updatedObject = requestProperties.getTransitionObject();
        updatedObject.setExternalId(requestProperties.getId());

        String arangoObjectKey = ArangoHelper.getKeyFromId(foundObject.getId());
        getDb().collection(requestProperties.getEntityName())
                .replaceDocument(arangoObjectKey, updatedObject.toJsonObject());
    }

    @Override
    public void deleteEntity(RequestProperties requestProperties) {
        TransitionObject foundObject = getFirstEntity(requestProperties);
        log.debug("Found entity for delete: \n{}", foundObject);

        String arangoObjectKey = ArangoHelper.getKeyFromId(foundObject.getId());
        getDb().collection(requestProperties.getEntityName())
                .deleteDocument(arangoObjectKey);
    }

    @Override
    public TransitionObject getFirstEntity(RequestProperties requestProperties) {
        return queryEntities(requestProperties).stream()
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Object with id: {} not found", requestProperties.getId());
                    return new EntityNotFound("Object with id: " + requestProperties.getId() + " not found");
                });
    }

    private List<TransitionObject> queryEntities(RequestProperties requestProperties) {
        Query query = QueryFactory.fromRequestProperties(requestProperties);
        String aqlQuery = query.getQuery();
        Map<String, Object> bindings = query.getBindings();
        log.debug("Executing following AQL query:\n{}\nWith bindings: {}", aqlQuery, bindings);

        List<BaseDocument> documents = getDb()
                .query(aqlQuery, bindings, null, BaseDocument.class)
                .asListRemaining();

        return transitionObjectConverter.convert(documents);
    }

    private ArangoDatabase getDb() {
        return datasource.getDb();
    }
}
