package com.dmfe.tof.dataproc.components.request.handlers;

import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.components.request.data.RequestProperties;
import com.dmfe.tof.dataproc.components.validation.Validator;
import com.dmfe.tof.dataproc.services.api.TreeCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostRequestHandler implements RequestHandler<String> {

    private final TreeCreateService treeCreateService;
    private final Validator validator;

    @Override
    public String handle(RequestData data) {
        validator.validate(data);

        return treeCreateService.saveEntity(RequestProperties.fromRequestData(data));
    }
}
