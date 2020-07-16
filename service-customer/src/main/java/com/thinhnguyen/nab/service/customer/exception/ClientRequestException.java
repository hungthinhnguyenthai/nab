package com.thinhnguyen.nab.service.customer.exception;

import lombok.Data;


@Data
public class ClientRequestException extends RuntimeException {
    private Integer status;
    public ClientRequestException(Integer status, String message) {
        super(message);
        this.status = status;
    }
}
