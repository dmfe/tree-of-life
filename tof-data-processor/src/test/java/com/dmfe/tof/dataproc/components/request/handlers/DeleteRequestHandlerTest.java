package com.dmfe.tof.dataproc.components.request.handlers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.components.request.data.RequestProperties;
import com.dmfe.tof.dataproc.services.api.TreeDeleteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class DeleteRequestHandlerTest {

    @Test
    void testHandle(@Mock TreeDeleteService treeDeleteService) {
        DeleteRequestHandler deleteRequestHandler = new DeleteRequestHandler(treeDeleteService);
        RequestData requestData = RequestData.builder()
                .id("1")
                .entity("test_entity").build();
        RequestProperties requestProperties = RequestProperties.fromRequestData(requestData);

        deleteRequestHandler.handle(requestData);

        verify(treeDeleteService, times(1)).deleteEntity(requestProperties);
    }
}
