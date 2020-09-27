package com.dmfe.tof.dataproc.dao.converters;

import static com.dmfe.tof.dataproc.utils.ArangoHelper.ARANGO_DELIMITER;
import static com.dmfe.tof.dataproc.utils.ArangoHelper.EXTERNAL_ID;

import com.arangodb.entity.BaseDocument;
import com.dmfe.tof.dataproc.dao.dto.ArangoTransitionObject;
import com.dmfe.tof.dataproc.dao.dto.TransitionObject;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ArangoTransitionObjectConverter implements TransitionObjectConverter<BaseDocument> {

    @Override
    public List<TransitionObject> convert(List<? extends BaseDocument> entities) {
        return entities.stream()
                .map(baseDocument -> {
                    Map<String, Object> properties = baseDocument.getProperties();
                    String id = baseDocument.getId();
                    String externalId = (String) properties.get(EXTERNAL_ID);

                    return ArangoTransitionObject.builder()
                            .id(id)
                            .externalId(externalId)
                            .collectionName(getCollectionName(id))
                            .properties(properties)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private String getCollectionName(String arangoId) {
        return Optional.ofNullable(arangoId)
                .map(id -> Stream.of(id.split(ARANGO_DELIMITER)).findFirst().orElse(""))
                .orElse("");
    }
}
