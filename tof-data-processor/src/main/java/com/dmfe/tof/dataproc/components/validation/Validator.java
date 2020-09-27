package com.dmfe.tof.dataproc.components.validation;

import com.dmfe.tof.dataproc.components.request.data.RequestData;

public interface Validator {
    void validate(RequestData data);
}
