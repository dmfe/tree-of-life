package com.dmfe.tof.dataproc.dao.queries;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class ArangoFilterQuery implements Query {

    private static final String ARANGO_FILTER_EXPRESSION = "<FILTER_EXPRESSION>";
    private static final String ARANGO_FILTER_QUERY_PART =
            "FILTER " + ARANGO_FILTER_EXPRESSION;

    private final Query baseQuery;
    private final Map<String, Object> filteringParamValues;

    @Override
    public String getQuery() {
        return baseQuery.getQuery().replace(FILTER_EXTENSION_POINT, createArangoFilterPart());
    }

    @Override
    public Map<String, Object> getBindings() {
        Map<String, Object> bindings = new HashMap<>(baseQuery.getBindings());
        bindings.putAll(filteringParamValues);

        return bindings;
    }

    private String createArangoFilterPart() {
        String filteringExpression = filteringParamValues.entrySet().stream()
                .map(entry -> ENTITY + ".`" + entry.getKey() + "` == " + "@" + entry.getKey())
                .collect(Collectors.joining(" && "));

        return ARANGO_FILTER_QUERY_PART.replace(ARANGO_FILTER_EXPRESSION, filteringExpression);
    }
}
