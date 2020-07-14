package com.thinhnguyen.nab.service.prepaiddata.rest;

import com.thinhnguyen.nab.service.prepaiddata.dto.InvoiceDto;
import com.thinhnguyen.nab.service.prepaiddata.dto.VoucherDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TelecomRestClient {

    @POST("/v1/data/{type}/purchase")
    Call<VoucherDto> purchaseData(@Path("type") String type, @Body InvoiceDto dto);
}
