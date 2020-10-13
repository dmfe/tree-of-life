package com.dmfe.tof.dataproc.dao.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.exceptions.JsonFormatException;
import org.junit.jupiter.api.Test;

import java.util.Map;

class TransitionObjectFactoryTest {

    @Test
    void testFromRequestDataSunnyDayScenario() {
        String id = "1";
        String entity = "test_entity";
        String propertyName = "test";
        String propertyValue = "test_value";
        String data = "{\"" + propertyName + "\":\"" + propertyValue + "\"}";
        TransitionObject expectedTransitionObject = ArangoTransitionObject.builder()
                .externalId(id)
                .collectionName(entity)
                .properties(Map.of(propertyName, propertyValue))
                .build();

        RequestData requestData = RequestData.builder()
                .id(id)
                .entity(entity)
                .data(data)
                .build();
        TransitionObject actualTransitionObject = TransitionObjectFactory.fromRequestData(requestData);

        assertEquals(expectedTransitionObject, actualTransitionObject);
    }

    @Test
    void testFromRequestDataIncorrectJsonFormat() {
        String invalidJson = "{";
        String expectedErrorMsg = "Incorrect json format:\n" + invalidJson;
        RequestData requestData = RequestData.builder()
                .data(invalidJson)
                .build();

        Throwable exception = assertThrows(JsonFormatException.class, () ->
                TransitionObjectFactory.fromRequestData(requestData));

        assertEquals(expectedErrorMsg, exception.getMessage());
    }
}
