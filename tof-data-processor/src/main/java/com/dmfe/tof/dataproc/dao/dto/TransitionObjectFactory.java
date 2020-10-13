package com.dmfe.tof.dataproc.dao.dto;

import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.exceptions.JsonFormatException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

public final class TransitionObjectFactory {

    private static final Gson GSON = new Gson();

    private TransitionObjectFactory() {
    }

    public static TransitionObject fromRequestData(RequestData data) {

        return ArangoTransitionObject.builder()
                .externalId(data.getId())
                .collectionName(data.getEntity())
                .properties(createProperties(data.getData()))
                .build();
    }

    private static Map<String, Object> createProperties(String data) {
        Type tokenType = new TypeToken<Map<String, Object>>() {}.getType();

        if (StringUtils.isNotEmpty(data)) {
            try {
                return GSON.fromJson(data, tokenType);
            } catch (JsonSyntaxException ex) {
                throw new JsonFormatException("Incorrect json format:\n" + data, ex);
            }
        } else {
            return Collections.emptyMap();
        }
    }
}
