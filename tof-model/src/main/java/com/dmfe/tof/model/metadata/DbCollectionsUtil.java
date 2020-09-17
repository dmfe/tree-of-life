package com.dmfe.tof.model.metadata;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class DbCollectionsUtil {

    private static final Map<Integer, String> DB_COLLECTION_NAMES_MAP = Map.of(
            DbCollections.PERSON.getNumber(), "person",
            DbCollections.LOCATION.getNumber(), "location"
    );

    private DbCollectionsUtil() {
    }

    public static List<DbCollections> getDbCollectionsValues() {
        return Arrays.stream(DbCollections.values())
                .filter(collection -> collection != DbCollections.UNRECOGNIZED)
                .collect(Collectors.toList());
    }

    public static Optional<String> getCollectionName(DbCollections collection) {
        return Optional.ofNullable(DB_COLLECTION_NAMES_MAP.get(collection.getNumber()));
    }
}
