package com.dmfe.tof.dataproc.components.request.handlers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.components.request.data.RequestProperties;
import com.dmfe.tof.dataproc.components.validation.Validator;
import com.dmfe.tof.dataproc.services.api.TreeCreateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class PostRequestHandlerTest {

    @Test
    void testHandle(@Mock TreeCreateService treeCreateService, @Mock Validator validator) {
        PostRequestHandler postRequestHandler = new PostRequestHandler(treeCreateService, validator);
        RequestData requestData = RequestData.builder()
                .entity("test_entity")
                .data("{\"a\":\"a_val\"}").build();
        RequestProperties requestProperties = RequestProperties.fromRequestData(requestData);

        postRequestHandler.handle(requestData);

        verify(validator, times(1)).validate(requestData);
        verify(treeCreateService, times(1)).saveEntity(requestProperties);
    }
}
