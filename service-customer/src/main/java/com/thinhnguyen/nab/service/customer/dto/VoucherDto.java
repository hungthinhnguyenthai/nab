package com.thinhnguyen.nab.service.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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
