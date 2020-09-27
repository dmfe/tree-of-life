package com.dmfe.tof.dataproc.dao.dto;

public interface TransitionObject {
    String getId();
    void setExternalId(String id);
    String getExternalId();
    String getEntityName();
    String toJsonObject();
}
