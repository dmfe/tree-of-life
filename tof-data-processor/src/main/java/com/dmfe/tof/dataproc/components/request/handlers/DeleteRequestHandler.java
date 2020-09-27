package com.dmfe.tof.dataproc.components.request.handlers;

import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.components.request.data.RequestProperties;
import com.dmfe.tof.dataproc.services.api.TreeDeleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteRequestHandler implements RequestHandler<Void> {

    private final TreeDeleteService treeDeleteService;

    @Override
    public Void handle(RequestData data) {
        treeDeleteService.deleteEntity(RequestProperties.fromRequestData(data));
        return null;
    }
}
