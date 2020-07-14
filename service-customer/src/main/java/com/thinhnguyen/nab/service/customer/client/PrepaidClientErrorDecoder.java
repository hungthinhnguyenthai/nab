package com.thinhnguyen.nab.service.customer.client;

import com.thinhnguyen.nab.service.customer.exception.ResponseException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;

import static feign.FeignException.errorStatus;

@Component
public class PrepaidClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        if(response.status() >=400 && response.status() <= 599) {
            return new ResponseException(response.status(), response.reason());
        }
        return errorStatus(s, response);
    }
}
