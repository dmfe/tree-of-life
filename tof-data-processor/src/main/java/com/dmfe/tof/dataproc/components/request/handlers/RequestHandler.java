package com.dmfe.tof.dataproc.components.request.handlers;

import com.dmfe.tof.dataproc.components.request.data.RequestData;

public interface RequestHandler<R> {
    R handle(RequestData data);
}
