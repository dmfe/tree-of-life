package com.dmfe.tof.dataproc.components.request.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class RequestData {
    private String id;
    private String entity;
    private String data;
}
