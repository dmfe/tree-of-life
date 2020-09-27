package com.dmfe.tof.dataproc.dao.queries;

import static com.dmfe.tof.dataproc.utils.ArangoHelper.EXTERNAL_ID;

import com.dmfe.tof.dataproc.components.request.data.RequestProperties;

import java.util.Map;

public class QueryFactory {
    public static Query fromRequestProperties(RequestProperties requestProperties) {
        Query baseQuery = new ArangoListQuery(requestProperties.getEntityName());

        if (requestProperties.isListRequest()) {
            return baseQuery;
        } else {
            return new ArangoFilterQuery(baseQuery,
                    Map.of(EXTERNAL_ID, requestProperties.getId())
            );
        }
    }
}
