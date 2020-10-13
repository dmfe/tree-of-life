package com.dmfe.tof.dataproc.exceptions;

import com.dmfe.tof.dataproc.components.request.data.ErrorData;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ApiErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorData handleNotFoundErrors(Throwable ex) {
        log.warn(ex.getLocalizedMessage());

        return new ErrorData(ex.getLocalizedMessage());
    }

    @ExceptionHandler({
            ModelException.class,
            ProtobufFormatException.class,
            JsonFormatException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorData handleBadRequestErrors(Throwable ex) {
        log.warn(ex.getLocalizedMessage());

        return new ErrorData(ex.getLocalizedMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorData handleFallbackError(Throwable ex) {
        log.error("Internal server error: {}", ex.getLocalizedMessage(), ex);

        return new ErrorData("Internal server error: " + ex.getLocalizedMessage());
    }
}
