package com.dmfe.tof.dataproc.dao.dto;

import static com.dmfe.tof.dataproc.utils.ArangoHelper.EXTERNAL_ID;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.ToString;

import java.util.Map;

@Builder
@ToString
public final class ArangoTransitionObject implements TransitionObject {

    private static final Gson GSON = new Gson();

    private String id;
    private String externalId;
    private String collectionName;
    private Map<String, Object> properties;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getExternalId() {
        return externalId;
    }

    @Override
    public void setExternalId(String id) {
        externalId = id;
        properties.put(EXTERNAL_ID, id);
    }

    @Override
    public String getEntityName() {
        return collectionName;
    }

    @Override
    public String toJsonObject() {
        return GSON.toJson(properties);
    }
}
