package com.thinhnguyen.nab.service.customer.client;

import com.thinhnguyen.nab.service.customer.dto.VoucherDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="service-prepaid-data")
public interface PrepaidDataServiceClient {
    @PostMapping("v1/data/{type}/purchase")
    VoucherDto getDataVoucher(@PathVariable("type") String type);
}
