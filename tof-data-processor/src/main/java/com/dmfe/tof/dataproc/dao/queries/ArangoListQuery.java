package com.dmfe.tof.dataproc.dao.queries;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
class ArangoListQuery implements Query {

    private static final String COLLECTION = "@collection";
    private static final String ARANGO_LIST_QUERY = "FOR " + ENTITY + " IN @" + COLLECTION + "\n" +
            FILTER_EXTENSION_POINT + "\n" +
            "RETURN " + ENTITY;

    private final String collection;


    @Override
    public String getQuery() {
        return ARANGO_LIST_QUERY;
    }

    @Override
    public Map<String, Object> getBindings() {
        return Map.of(COLLECTION, collection);
    }
}
