package com.dmfe.tof.dataproc.components.validation.protobuf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.dmfe.tof.dataproc.exceptions.ProtobufFormatException;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.jupiter.api.Test;

class InvalidProtobufMsgHandlerTest {

    @Test
    void testHandleSunnyDayScenario() {
        String expected = "test";

        String actual = InvalidProtobufMsgHandler.handle(() -> expected);

        assertEquals(expected, actual);
    }

    @Test
    void testHandleWithException() {
        String invalidProtoBufMsg = "Invalid ProtoBuf test exception...";
        String expectedErrMsg = "Error while parsing protobuf message: " + invalidProtoBufMsg;

        Throwable exception = assertThrows(ProtobufFormatException.class, () ->
                InvalidProtobufMsgHandler.handle(() -> {
                    throw new InvalidProtocolBufferException(invalidProtoBufMsg);
                })
        );

        assertEquals(expectedErrMsg, exception.getMessage());
    }
}
