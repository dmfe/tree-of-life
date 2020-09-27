package com.dmfe.tof.dataproc.dao.converters;

import com.dmfe.tof.dataproc.dao.dto.TransitionObject;

import java.util.List;

public interface TransitionObjectConverter<T> {
    List<TransitionObject> convert(List<? extends T> entities);
}
