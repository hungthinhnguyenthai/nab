package com.thinhnguyen.nab.service.customer.exception;

import lombok.Data;


@Data
public class ResponseException extends RuntimeException {
    private Integer status;
    public ResponseException(Integer status, String message) {
        super(message);
        this.status = status;
    }
}
