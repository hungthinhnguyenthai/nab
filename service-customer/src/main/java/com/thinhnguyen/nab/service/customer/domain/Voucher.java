package com.thinhnguyen.nab.service.customer.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Voucher {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long id;

    @Basic
    @Column(name = "customer")
    String customer;

    @Basic
    @Column(name = "voucher_code")
    String voucherCode;

    @Basic
    @Column(name = "expired_date")
    ZonedDateTime expiredDate;

    @Basic
    @Column(name = "completed")
    private Boolean completed;

}
