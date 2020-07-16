package com.thinhnguyen.nab.service.prepaiddata.exception;

import lombok.Data;

@Data
public class RequestException extends RuntimeException {
    private int code;
    public RequestException(int code, String message) {
        super(message);
        this.code = code;
    }
}
