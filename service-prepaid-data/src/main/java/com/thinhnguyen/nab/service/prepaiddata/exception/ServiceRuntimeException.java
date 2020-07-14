package com.thinhnguyen.nab.service.prepaiddata.exception;

import lombok.Getter;

@Getter
public class ServiceRuntimeException extends RuntimeException {

    private String errorID;

    public ServiceRuntimeException(String errorID, String message, Throwable cause) {
        super(message, cause);
        this.errorID = errorID;
    }

    public ServiceRuntimeException(String errorID, String message) {
        super(message);
        this.errorID = errorID;
    }

}
