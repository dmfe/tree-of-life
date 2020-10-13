package com.dmfe.tof.dataproc.dao.converters;

import static com.dmfe.tof.dataproc.utils.ArangoHelper.EXTERNAL_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentField;
import com.dmfe.tof.dataproc.dao.dto.ArangoTransitionObject;
import com.dmfe.tof.dataproc.dao.dto.TransitionObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ArangoTransitionObjectConverterTest {

    @Test
    void testConvert() {
        String arangoIdOne = "persons/1";
        String arangoIdTwo = "persons/2";
        String externalIdOne = "test_external_id_one";
        String externalIdTwo = "test_external_id_two";
        String propertyOneName = "testPropertyOne";
        String propertyOneValue = "testPropertyOneValue";
        String propertyTwoName = "testPropertyTwo";
        String propertyTwoValue = "testPropertyTwoValue";

        List<BaseDocument> baseDocuments = List.of(
                new BaseDocument(new HashMap<>(Map.of(
                        DocumentField.Type.ID.getSerializeName(), arangoIdOne,
                        EXTERNAL_ID, externalIdOne,
                        propertyOneName, propertyOneValue
                ))),
                new BaseDocument(new HashMap<>(Map.of(
                        DocumentField.Type.ID.getSerializeName(), arangoIdTwo,
                        EXTERNAL_ID, externalIdTwo,
                        propertyTwoName, propertyTwoValue
                )))
        );

        List<TransitionObject> expectedTransitionObjects = List.of(
                ArangoTransitionObject.builder()
                        .id(arangoIdOne)
                        .externalId(externalIdOne)
                        .collectionName("persons")
                        .properties(Map.of(
                                EXTERNAL_ID, externalIdOne,
                                propertyOneName, propertyOneValue
                        ))
                        .build(),
                ArangoTransitionObject.builder()
                        .id(arangoIdTwo)
                        .externalId(externalIdTwo)
                        .collectionName("persons")
                        .properties(Map.of(
                                EXTERNAL_ID, externalIdTwo,
                                propertyTwoName, propertyTwoValue

                        ))
                        .build()
        );

        List<TransitionObject> actualTransitionObjects = new ArangoTransitionObjectConverter().convert(baseDocuments);

        assertEquals(expectedTransitionObjects, actualTransitionObjects);
    }
}
