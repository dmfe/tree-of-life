package com.dmfe.tof.dataproc.components.request.handlers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dmfe.tof.dataproc.services.api.DbInitService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class RequestHandlerProviderTest {

    @TestConfiguration
    static class RequestHandlerProviderTestContextConfiguration {
        @Bean
        public DbInitService dbInitService() {
            return () -> {};
        }
    }

    @Autowired
    private RequestHandlerProvider requestHandlerProvider;

    @Test
    void testObtainGetRequestHandler() {
        RequestHandler<String> requestHandler = requestHandlerProvider.getGetRequestHandler();

        assertTrue(requestHandler instanceof GetRequestHandler);
    }

    @Test
    void testObtainPostRequestHandler() {
        RequestHandler<String> requestHandler = requestHandlerProvider.getPostRequestHandler();

        assertTrue(requestHandler instanceof PostRequestHandler);
    }

    @Test
    void testObtainPutRequestHandler() {
        RequestHandler<Void> requestHandler = requestHandlerProvider.getPutRequestHandler();

        assertTrue(requestHandler instanceof PutRequestHandler);
    }

    @Test
    void testObtainDeleteRequestHandler() {
        RequestHandler<Void> requestHandler = requestHandlerProvider.getDeleteRequestHandler();

        assertTrue(requestHandler instanceof DeleteRequestHandler);
    }
}
