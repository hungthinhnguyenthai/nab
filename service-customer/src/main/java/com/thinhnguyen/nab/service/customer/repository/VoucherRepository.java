package com.thinhnguyen.nab.service.customer.repository;

import com.thinhnguyen.nab.service.customer.domain.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    List<Voucher> findByCustomer(String customerId);
}
