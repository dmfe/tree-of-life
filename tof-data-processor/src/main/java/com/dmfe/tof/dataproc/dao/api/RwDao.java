package com.dmfe.tof.dataproc.dao.api;

import com.dmfe.tof.dataproc.components.request.data.RequestProperties;
import com.dmfe.tof.dataproc.dao.dto.TransitionObject;

import java.util.List;

public interface RwDao {
    List<TransitionObject> getEntities(RequestProperties requestProperties);
    TransitionObject getFirstEntity(RequestProperties requestProperties);
    String createEntity(RequestProperties requestProperties);
    void replaceEntity(RequestProperties requestProperties);
    void deleteEntity(RequestProperties requestProperties);
}
