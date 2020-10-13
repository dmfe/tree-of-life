package com.dmfe.tof.model.metadata;

import com.dmfe.tof.model.tree.Location;
import com.dmfe.tof.model.tree.Person;
import com.google.protobuf.Message;
import com.google.protobuf.MessageOrBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class DbCollectionsUtil {

    private static class EntityInfo {

        private final String entityCollectionName;
        private final MessageOrBuilder protoBuilder;

        private EntityInfo(String collectionName, Message.Builder builder) {
            entityCollectionName = collectionName;
            protoBuilder = builder;
        }
    }

    private static final Map<Integer, EntityInfo> DB_ENTITIES_MAP = Map.of(
            DbCollections.PERSONS.getNumber(), new EntityInfo("persons", Person.newBuilder()),
            DbCollections.LOCATIONS.getNumber(), new EntityInfo("locations", Location.newBuilder())
    );

    private DbCollectionsUtil() {
    }

    public static List<DbCollections> getDbCollectionsValues() {
        return Arrays.stream(DbCollections.values())
                .filter(collection -> collection != DbCollections.UNRECOGNIZED)
                .collect(Collectors.toList());
    }

    public static Optional<String> getCollectionName(DbCollections collection) {
        return Optional.ofNullable(DB_ENTITIES_MAP.get(collection.getNumber()))
                .map(entityInfo -> entityInfo.entityCollectionName);
    }

    public static Optional<Message> getProtoMessage(String entityName) {
        try {
            int dbCollectionNumber = DbCollections.valueOf(entityName.toUpperCase()).getNumber();
            return Optional.ofNullable(DB_ENTITIES_MAP.get(dbCollectionNumber))
                    .map(entityInfo -> entityInfo.protoBuilder.getDefaultInstanceForType());
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }
}
