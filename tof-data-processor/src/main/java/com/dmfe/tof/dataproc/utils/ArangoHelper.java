package com.dmfe.tof.dataproc.utils;

import java.util.Optional;
import java.util.stream.Stream;

public final class ArangoHelper {

    public static String EXTERNAL_ID = "id";
    public static String ARANGO_DELIMITER = "/";

    private ArangoHelper() {
    }

    public static String getKeyFromId(String id) {
        return Optional.ofNullable(id)
                .map(it ->
                        Stream.of(it.split(ARANGO_DELIMITER))
                                .reduce((first, second) -> second)
                                .orElse("")
                )
                .orElse("");
    }
}
