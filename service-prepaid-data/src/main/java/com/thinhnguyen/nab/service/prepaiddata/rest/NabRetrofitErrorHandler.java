package com.thinhnguyen.nab.service.prepaiddata.rest;

import com.thinhnguyen.nab.service.prepaiddata.exception.RequestException;
import retrofit2.Response;

public interface NabRetrofitErrorHandler<T>  {

    T handle(Response<T> t) throws RequestException;
}
