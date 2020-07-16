package com.thinhnguyen.nab.service.prepaiddata.controller;

import com.thinhnguyen.nab.service.prepaiddata.concurrent.Superviser;
import com.thinhnguyen.nab.service.prepaiddata.dto.VoucherDto;
import com.thinhnguyen.nab.service.prepaiddata.service.PrePaidDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrepaidDataController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrepaidDataController.class);


    @Autowired
    private PrePaidDataService service;

    @PostMapping("v1/data/{type}/purchase")
    public ResponseEntity<VoucherDto> payData(@PathVariable("type") String dataType) {

        Superviser superviser = Superviser.<VoucherDto>builder()
                .task(() -> service.payData(dataType))
                .timeoutInSec(30)
                .failInstruction(o -> service.updateVoucher(o)).build();

        return superviser.overseeing();
    }
}
