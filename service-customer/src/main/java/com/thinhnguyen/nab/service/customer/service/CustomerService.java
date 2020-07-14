package com.thinhnguyen.nab.service.customer.service;

import com.thinhnguyen.nab.service.customer.client.PrepaidDataServiceClient;
import com.thinhnguyen.nab.service.customer.domain.Voucher;
import com.thinhnguyen.nab.service.customer.dto.VoucherDto;
import com.thinhnguyen.nab.service.customer.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    PrepaidDataServiceClient serviceClient;

    @Autowired
    VoucherRepository voucherRepository;

    public VoucherDto purchaseDataVoucher(String phoneNum, String dataType) {
        VoucherDto voucherDto = serviceClient.getDataVoucher(dataType);
        if(voucherDto == null) return null;
        Voucher voucher = new Voucher();
        voucher.setCustomer(phoneNum);
        voucher.setVoucherCode(voucherDto.getVoucherCode());
        voucher.setExpiredDate(voucherDto.getExpiredDate());
        voucher.setCompleted(Boolean.TRUE);
        voucherRepository.save(voucher);
        return voucherDto;
    }

    public VoucherDto updateDataVoucher(VoucherDto request) {
        Voucher voucher = new Voucher();
        voucher.setCustomer(request.getPhone());
        voucher.setVoucherCode(request.getVoucherCode());
        voucher.setExpiredDate(request.getExpiredDate());
        voucher.setCompleted(Boolean.TRUE);
        voucher = voucherRepository.save(voucher);
        request.setId(String.valueOf(voucher.getId()));
        return request;
    }

    public List<VoucherDto> getCustomerVouchers(String phone) {
        List<Voucher> vouchers = voucherRepository.findByCustomer(phone);
        return vouchers.stream()
                .map(voucher -> new VoucherDto(voucher.getVoucherCode(), voucher.getExpiredDate()))
                .collect(Collectors.toList());
    }
}
