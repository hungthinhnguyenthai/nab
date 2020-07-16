package com.thinhnguyen.nab.service.customer.controller;

import com.thinhnguyen.nab.service.customer.dto.PurchaseRequestDto;
import com.thinhnguyen.nab.service.customer.dto.VoucherDto;
import com.thinhnguyen.nab.service.customer.exception.ClientRequestException;
import com.thinhnguyen.nab.service.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("v1/data-voucher/purchase")
    public ResponseEntity<VoucherDto> purchaseDataVoucher(@Validated @RequestBody PurchaseRequestDto request) {
        try {
            VoucherDto voucherDto = customerService.purchaseDataVoucher(request.getPhone(), request.getDataType());
            return ResponseEntity.ok(voucherDto);
        } catch (ClientRequestException e) {
            HttpStatus errorCode = HttpStatus.resolve(e.getStatus());
            if(errorCode != null) {
                switch (errorCode){
                    case SERVICE_UNAVAILABLE:
                        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
                    case ACCEPTED:
                        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
                    case BAD_REQUEST:
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("v1/data-voucher/update")
    public ResponseEntity<VoucherDto> updateDataVoucher(@RequestBody VoucherDto request) {
        VoucherDto voucherDto = customerService.updateDataVoucher(request);
        if(voucherDto != null) {
            return ResponseEntity.ok(voucherDto);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("v1/{phone}/data-vouchers")
    public ResponseEntity<List<VoucherDto>> getVoucher(@PathVariable("phone") String phone) {
        return ResponseEntity.ok(customerService.getCustomerVouchers(phone));
    }


}
