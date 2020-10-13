package com.dmfe.tof.dataproc.components.validation.protobuf;

import com.dmfe.tof.dataproc.exceptions.ProtobufFormatException;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.log4j.Log4j2;

@Log4j2
final class InvalidProtobufMsgHandler {

    private InvalidProtobufMsgHandler() {}

    static <R> R handle(InvalidProtobufMsgThrower<R> thrower) {
        try {
            return thrower.doWork();
        } catch (InvalidProtocolBufferException ex) {
            throw new ProtobufFormatException("Error while parsing protobuf message: " + ex.getLocalizedMessage(), ex);
        }
    }
}
