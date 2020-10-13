package com.dmfe.tof.dataproc.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.components.request.handlers.DeleteRequestHandler;
import com.dmfe.tof.dataproc.components.request.handlers.GetRequestHandler;
import com.dmfe.tof.dataproc.components.request.handlers.PostRequestHandler;
import com.dmfe.tof.dataproc.components.request.handlers.PutRequestHandler;
import com.dmfe.tof.dataproc.components.request.handlers.RequestHandler;
import com.dmfe.tof.dataproc.components.request.handlers.RequestHandlerProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@WebMvcTest(TOFDataProcessorController.class)
class TOFDataProcessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestHandlerProvider requestHandlerProvider;

    @Test
    void testGetPersons() throws Exception {
        String expectedEntitiesList = "[{\"test\":\"test_value\"}]";
        RequestData getListEntitiesRequest = RequestData.builder()
                .entity("persons")
                .build();

        RequestHandler<String> getRequestHandlerMock = mock(GetRequestHandler.class);
        when(getRequestHandlerMock.handle(eq(getListEntitiesRequest))).thenReturn(expectedEntitiesList);
        when(requestHandlerProvider.getGetRequestHandler()).thenReturn(getRequestHandlerMock);

        mockMvc.perform(get("/api/tof/dp/tree/persons"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedEntitiesList)));
    }

    @Test
    void testGetPersonById() throws Exception {
        String expectedEntity = "{\"test\":\"test_value\"}";
        RequestData getEntityByIdRequest = RequestData.builder()
                .id("1")
                .entity("persons")
                .build();

        RequestHandler<String> getRequestHandlerMock = mock(GetRequestHandler.class);
        when(getRequestHandlerMock.handle(eq(getEntityByIdRequest))).thenReturn(expectedEntity);
        when(requestHandlerProvider.getGetRequestHandler()).thenReturn(getRequestHandlerMock);

        mockMvc.perform(get("/api/tof/dp/tree/persons/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedEntity)));
    }

    @Test
    void testAddPerson() throws Exception {
        String expectedExternalId = "1";
        String expectedCreationMsg = "Created Person with id = " + expectedExternalId;
        String body = "{\"test\":\"test_value\"}";
        RequestData postEntityRequest = RequestData.builder()
                .entity("persons")
                .data(body)
                .build();

        RequestHandler<String> postRequestHandlerMock = mock(PostRequestHandler.class);
        when(postRequestHandlerMock.handle(eq(postEntityRequest))).thenReturn(expectedExternalId);
        when(requestHandlerProvider.getPostRequestHandler()).thenReturn(postRequestHandlerMock);

        mockMvc.perform(post("/api/tof/dp/tree/persons").contentType("application/json").content(body))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(expectedCreationMsg)));
    }

    @Test
    void testModifyPerson() throws Exception {
        String externalId = "1";
        String expectedModificationMsg = "Modified Person with id = " + externalId;
        String body = "{\"test\":\"test_value\"}";
        RequestData expectedPutEntityRequest = RequestData.builder()
                .id(externalId)
                .entity("persons")
                .data(body)
                .build();

        RequestHandler<Void> putRequestHandlerMock = mock(PutRequestHandler.class);
        ArgumentCaptor<RequestData> requestDataArgumentCaptor = ArgumentCaptor.forClass(RequestData.class);
        doNothing().when(putRequestHandlerMock).handle(requestDataArgumentCaptor.capture());
        when(requestHandlerProvider.getPutRequestHandler()).thenReturn(putRequestHandlerMock);

        mockMvc.perform(put("/api/tof/dp/tree/persons/1").contentType("application/json").content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedModificationMsg)));

        assertEquals(expectedPutEntityRequest, requestDataArgumentCaptor.getValue());
    }

    @Test
    void testDeletePerson() throws Exception {
        String externalId = "1";
        String expectedDeletionMsg = "Deleted Person with id = " + externalId;
        RequestData expectedDeleteEntityRequest = RequestData.builder()
                .id(externalId)
                .entity("persons")
                .build();

        RequestHandler<Void> deleteRequestHandlerMock = mock(DeleteRequestHandler.class);
        ArgumentCaptor<RequestData> requestDataArgumentCaptor = ArgumentCaptor.forClass(RequestData.class);
        doNothing().when(deleteRequestHandlerMock).handle(requestDataArgumentCaptor.capture());
        when(requestHandlerProvider.getDeleteRequestHandler()).thenReturn(deleteRequestHandlerMock);

        mockMvc.perform(delete("/api/tof/dp/tree/persons/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedDeletionMsg)));

        assertEquals(expectedDeleteEntityRequest, requestDataArgumentCaptor.getValue());
    }
}
