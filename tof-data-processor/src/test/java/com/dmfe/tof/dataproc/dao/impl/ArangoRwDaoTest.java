package com.dmfe.tof.dataproc.dao.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDatabase;
import com.arangodb.entity.BaseDocument;
import com.arangodb.entity.DocumentCreateEntity;
import com.dmfe.tof.dataproc.components.request.data.RequestData;
import com.dmfe.tof.dataproc.components.request.data.RequestProperties;
import com.dmfe.tof.dataproc.dao.converters.TransitionObjectConverter;
import com.dmfe.tof.dataproc.dao.datasources.Datasource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class ArangoRwDaoTest {

    private Datasource<ArangoDatabase> datasource;
    private TransitionObjectConverter<BaseDocument> transitionObjectConverter;
    private ArangoRwDao arangoRwDao;

    @BeforeAll
    void iniMocks() {
        datasource = mock(Datasource.class);
        transitionObjectConverter = mock(TransitionObjectConverter.class);
        arangoRwDao = new ArangoRwDao(datasource, transitionObjectConverter);
    }

    @Test
    void getEntities() {
        ArangoDatabase arangoDatabaseMock = mockArangoDb();
        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Map<String, Object>> bindVarsCaptor = ArgumentCaptor.forClass(Map.class);
        mockArangoQuery(arangoDatabaseMock, queryCaptor, bindVarsCaptor);

        RequestData requestData = RequestData.builder()
            .entity("test")
            .build();

        arangoRwDao.getEntities(RequestProperties.fromRequestData(requestData));

        String expectedQuery = "FOR entity IN @@collection\n" +
                               "/*(FILTER_EXTENSION_POINT)*/\n" +
                               "RETURN entity";
        Map<String, Object> expectedBindVars = Map.of("@collection", "test");

        assertAll(
                () -> assertEquals(expectedQuery, queryCaptor.getValue()),
                () -> assertEquals(expectedBindVars, bindVarsCaptor.getValue()),
                () -> verify(arangoDatabaseMock, times(1))
                        .query(eq(expectedQuery), eq(expectedBindVars), eq(null), eq(BaseDocument.class))
        );
    }

    @Test
    void createEntity() {
        ArangoDatabase arangoDatabaseMock = mockArangoDb();
        ArgumentCaptor<String> entityCaptor = ArgumentCaptor.forClass(String.class);
        ArangoCollection arangoCollectionMock = mockArangoCollection(arangoDatabaseMock, entityCaptor);
        ArgumentCaptor<String> jsonObjectCaptor = ArgumentCaptor.forClass(String.class);
        mockArangoCollectionInsert(arangoCollectionMock, jsonObjectCaptor);

        RequestData requestData = RequestData.builder()
                .entity("test")
                .data("{\"test\":\"test_value\"}")
                .build();

        arangoRwDao.createEntity(RequestProperties.fromRequestData(requestData));

        assertAll(
                () -> assertEquals("test", entityCaptor.getValue()),
                () -> assertTrue(jsonObjectCaptor.getValue()
                        .matches("\\{\"test\":\"test_value\",\"id\":\"[a-z0-9-]+\"}")),
                () -> verify(arangoDatabaseMock, times(1)).collection(eq("test")),
                () -> verify(arangoCollectionMock, times(1)).insertDocument(eq(jsonObjectCaptor.getValue()))
        );
    }

    @Test
    @Disabled
    void replaceEntity() {
        ArangoRwDao arangoRwDaoSpy = spy(arangoRwDao);
        ArangoDatabase arangoDatabaseMock = mockArangoDb();
        ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Map<String, Object>> bindVarsCaptor = ArgumentCaptor.forClass(Map.class);
        mockArangoQuery(arangoDatabaseMock, queryCaptor, bindVarsCaptor);

        ArgumentCaptor<String> entityCaptor = ArgumentCaptor.forClass(String.class);

        RequestData requestData = RequestData.builder()
                .id("test_id")
                .entity("test")
                .data("{\"test\":\"test_value\"}")
                .build();

        arangoRwDaoSpy.replaceEntity(RequestProperties.fromRequestData(requestData));
    }

    @Test
    void deleteEntity() {
    }

    @Test
    void getFirstEntity() {
    }

    private ArangoDatabase mockArangoDb() {
        ArangoDatabase arangoDatabaseMock = mock(ArangoDatabase.class);
        when(datasource.getDb()).thenReturn(arangoDatabaseMock);

        return arangoDatabaseMock;
    }

    private void mockArangoQuery(ArangoDatabase arangoDatabaseMock,
                                 ArgumentCaptor<String> queryCaptor,
                                 ArgumentCaptor<Map<String, Object>> bindVarsCaptor) {

        ArangoCursor<BaseDocument> cursorMock = mock(ArangoCursor.class);
        when(arangoDatabaseMock.query(queryCaptor.capture(), bindVarsCaptor.capture(),
                    eq(null), eq(BaseDocument.class))).thenReturn(cursorMock);
    }

    private ArangoCollection mockArangoCollection(ArangoDatabase arangoDatabaseMock,
                                                  ArgumentCaptor<String> entityCaptor) {

        ArangoCollection arangoCollectionMock = mock(ArangoCollection.class);
        when(arangoDatabaseMock.collection(entityCaptor.capture())).thenReturn(arangoCollectionMock);

        return arangoCollectionMock;
    }

    private void mockArangoCollectionInsert(ArangoCollection arangoCollectionMock, ArgumentCaptor<String> jsonObjectCaptor) {
        DocumentCreateEntity<String> documentEntityMock = mock(DocumentCreateEntity.class);
        when(arangoCollectionMock.insertDocument(jsonObjectCaptor.capture())).thenReturn(documentEntityMock);
    }
}

