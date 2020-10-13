package com.dmfe.tof.dataproc.components.request.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dmfe.tof.dataproc.dao.dto.TransitionObjectFactory;
import org.junit.jupiter.api.Test;

class RequestPropertiesTest {

    @Test
    void testFromRequestData() {
        RequestData requestData = RequestData.builder()
                .id("1")
                .entity("test_entity")
                .data("{\"a\":\"a_val\"}").build();
        RequestProperties expected = new RequestProperties("1", "test_entity",
                TransitionObjectFactory.fromRequestData(requestData));

        RequestProperties actual = RequestProperties.fromRequestData(requestData);

        assertEquals(expected, actual);
    }

    @Test
    void testIsListRequest() {
        RequestData requestData = RequestData.builder()
                .id("")
                .entity("test_entity")
                .data("{\"a\":\"a_val\"}").build();
        RequestProperties requestProperties = RequestProperties.fromRequestData(requestData);

        assertTrue(requestProperties.isListRequest());
    }


    @Test
    void testIsListNotRequest() {
        RequestData requestData = RequestData.builder()
                .id("1")
                .entity("test_entity")
                .data("{\"a\":\"a_val\"}").build();
        RequestProperties requestProperties = RequestProperties.fromRequestData(requestData);

        assertFalse(requestProperties.isListRequest());
    }
}
