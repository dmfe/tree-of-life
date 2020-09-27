package com.dmfe.tof.dataproc.components.request.handlers;

import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.components.request.data.RequestProperties;
import com.dmfe.tof.dataproc.services.api.TreeReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetRequestHandler implements RequestHandler<String> {

    private final TreeReadService treeReadService;

    @Override
    public String handle(RequestData data) {
        return treeReadService.getEntities(RequestProperties.fromRequestData(data));
    }
}
