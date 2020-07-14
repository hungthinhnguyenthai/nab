package com.thinhnguyen.nab.service.prepaiddata.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherDto {
    String id;
    String phone;
    String voucherCode;
    ZonedDateTime expiredDate;

    public VoucherDto(String voucherCode, ZonedDateTime expiredDate) {
        this.voucherCode = voucherCode;
        this.expiredDate = expiredDate;
    }
}
