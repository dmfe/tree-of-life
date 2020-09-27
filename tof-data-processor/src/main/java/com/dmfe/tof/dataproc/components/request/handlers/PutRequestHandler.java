package com.dmfe.tof.dataproc.components.request.handlers;

import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.components.request.data.RequestProperties;
import com.dmfe.tof.dataproc.components.validation.Validator;
import com.dmfe.tof.dataproc.services.api.TreeModifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PutRequestHandler implements RequestHandler<Void> {

    private final TreeModifyService treeModifyService;
    private final Validator validator;

    @Override
    public Void handle(RequestData data) {
        validator.validate(data);
        treeModifyService.modifyEntity(RequestProperties.fromRequestData(data));

        return null;
    }
}
