package com.dmfe.tof.dataproc.dao.queries;

import java.util.Map;

public interface Query {
    String ENTITY = "entity";
    String FILTER_EXTENSION_POINT = "/*(FILTER_EXTENSION_POINT)*/";

    String getQuery();
    Map<String, Object> getBindings();
}
