package com.dmfe.tof.dataproc.controllers;

import com.google.protobuf.InvalidProtocolBufferException;

public interface InvalidProtobufMsgThrower<R> {

    R doWork() throws InvalidProtocolBufferException;
}
