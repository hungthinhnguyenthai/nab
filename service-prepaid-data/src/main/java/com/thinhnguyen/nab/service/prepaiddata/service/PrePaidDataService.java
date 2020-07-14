package com.thinhnguyen.nab.service.prepaiddata.service;


import com.thinhnguyen.nab.service.prepaiddata.client.CustomerServiceClient;
import com.thinhnguyen.nab.service.prepaiddata.dto.InvoiceDto;
import com.thinhnguyen.nab.service.prepaiddata.dto.VoucherDto;
import com.thinhnguyen.nab.service.prepaiddata.exception.ServiceRuntimeException;
import com.thinhnguyen.nab.service.prepaiddata.rest.TelecomRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.function.Consumer;


@Service
public class PrePaidDataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrePaidDataService.class);


    @Autowired
    TelecomRestClient telecomRestClient;

    @Autowired
    CustomerServiceClient customerServiceClient;

    @Autowired
    CipherService cipherService;


    public VoucherDto updateVoucher(VoucherDto voucherDto){
        VoucherDto dto = customerServiceClient.updateDataVoucher(voucherDto);
        if(dto != null) {
            //Confirm to third-party service
            return dto;
        }
        //Send error for rollback-transaction
        return null;
    }

    public VoucherDto payData(String type){
        InvoiceDto body = new InvoiceDto();
        try {
            VoucherDto response = handleResponse(telecomRestClient.purchaseData(type, body), (error) -> {});
            response.setVoucherCode(cipherService.decrypt(response.getVoucherCode()));
            return response;
        } catch (Exception e) {
            LOGGER.error("Cannot execute request", e);
            throw new ServiceRuntimeException("ER010" ,"Cannot execute request");
        }
    }

    private <T> T handleResponse(Call<T> call, Consumer<Response<T>> errorHandler) {
        try {
            Response<T> response = call.execute();
            if (!response.isSuccessful()) {
                errorHandler.accept(response);
            } else {
                return response.body();
            }
        } catch (IOException e) {
            LOGGER.error("Cannot parse the json response", e);
            throw new ServiceRuntimeException("ER04" ,"Cannot parse the json response");
        }
        throw new ServiceRuntimeException("ER03" ,"Request is unsuccessful");
    }


}
