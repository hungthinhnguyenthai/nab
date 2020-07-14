package com.thinhnguyen.nab.service.prepaiddata.client;

import com.thinhnguyen.nab.service.prepaiddata.dto.VoucherDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="customer-service")
public interface CustomerServiceClient {

    @PostMapping("v1/data-voucher/update")
    VoucherDto updateDataVoucher(@RequestBody VoucherDto request);
}
