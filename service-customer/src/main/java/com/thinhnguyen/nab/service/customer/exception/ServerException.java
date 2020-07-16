package com.thinhnguyen.nab.service.customer.exception;

import lombok.Data;


@Data
public class ServerException extends RuntimeException {
    private Integer status;
    public ServerException(Integer status, String message) {
        super(message);
        this.status = status;
    }
}
