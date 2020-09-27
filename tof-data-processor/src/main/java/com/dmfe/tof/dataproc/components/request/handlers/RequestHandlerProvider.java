package com.dmfe.tof.dataproc.components.request.handlers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class RequestHandlerProvider {
    private final RequestHandler<String> getRequestHandler;
    private final RequestHandler<String> postRequestHandler;
    private final RequestHandler<Void> putRequestHandler;
    private final RequestHandler<Void> deleteRequestHandler;
}
