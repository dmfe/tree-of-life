package com.dmfe.tof.dataproc.components.validation.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public interface InvalidProtobufMsgThrower<R> {

    R doWork() throws InvalidProtocolBufferException;
}
