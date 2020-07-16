package com.thinhnguyen.nab.service.prepaiddata.exception;

import com.thinhnguyen.nab.service.prepaiddata.dto.ErrorDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationExceptionHandler.class);


    @ExceptionHandler(ServiceRuntimeException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ErrorDto handleServiceException(ServiceRuntimeException e) {
        LOGGER.error("Caught exception", e);
        return ErrorDto.builder().code(e.getErrorID()).message(e.getMessage()).build();
    }

    @ExceptionHandler(RequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDto handleBadRequestException(RequestException e) {
        LOGGER.error("Caught exception", e);
        return ErrorDto.builder().code("#TBD").message(e.getMessage()).build();
    }
}
