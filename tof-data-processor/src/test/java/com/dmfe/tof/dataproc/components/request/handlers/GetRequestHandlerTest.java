package com.dmfe.tof.dataproc.components.request.handlers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.components.request.data.RequestProperties;
import com.dmfe.tof.dataproc.services.api.TreeReadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class GetRequestHandlerTest {

    @Test
    void testHandle(@Mock TreeReadService treeReadService) {
        GetRequestHandler getRequestHandler = new GetRequestHandler(treeReadService);
        RequestData requestData = RequestData.builder()
                .id("1")
                .entity("test_entity").build();
        RequestProperties requestProperties = RequestProperties.fromRequestData(requestData);

        getRequestHandler.handle(requestData);

        verify(treeReadService, times(1)).getEntities(requestProperties);
    }
}
