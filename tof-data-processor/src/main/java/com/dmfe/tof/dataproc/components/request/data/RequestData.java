package com.dmfe.tof.dataproc.components.request.data;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestData {
    private String id;
    private String entity;
    private String data;
}
