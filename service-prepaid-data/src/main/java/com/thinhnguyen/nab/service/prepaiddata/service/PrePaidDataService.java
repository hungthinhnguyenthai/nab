package com.thinhnguyen.nab.service.prepaiddata.service;


import com.thinhnguyen.nab.service.prepaiddata.client.CustomerServiceClient;
import com.thinhnguyen.nab.service.prepaiddata.dto.InvoiceDto;
import com.thinhnguyen.nab.service.prepaiddata.dto.VoucherDto;
import com.thinhnguyen.nab.service.prepaiddata.exception.RequestException;
import com.thinhnguyen.nab.service.prepaiddata.exception.ServiceRuntimeException;
import com.thinhnguyen.nab.service.prepaiddata.rest.NabRetrofitErrorHandler;
import com.thinhnguyen.nab.service.prepaiddata.rest.TelecomRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;


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


    /**
     * 1. Perform http request to third-party.
     * 2. Handling the response - error
     * 3. Decrypt the voucher code with private key.
     * @param type - data package
     * @return Decrypted Voucher Dto
     */
    public VoucherDto payData(String type){
        InvoiceDto body = new InvoiceDto();
        VoucherDto response = handleResponse(telecomRestClient.purchaseData(type, body), (error) -> {
            throw new RequestException(error.code(), error.message());
        });
        response.setVoucherCode(cipherService.decrypt(response.getVoucherCode()));
        return response;
    }

    /**
     * Utils handling retrofit2 call and response.
     * @param call
     * @param errorHandler
     * @param <T>
     * @return
     */
    private <T> T handleResponse(Call<T> call, NabRetrofitErrorHandler<T> errorHandler) {
        try {
            Response<T> response = call.execute();
            if (!response.isSuccessful()) {
                return errorHandler.handle(response);
            } else {
                return response.body();
            }
        } catch (IOException e) {
            LOGGER.error("Cannot parse the json response", e);
            throw new ServiceRuntimeException("ER04" ,"Third-party is not available");
        }
    }


}
