package com.thinhnguyen.nab.service.prepaiddata.controller;

import com.thinhnguyen.nab.service.prepaiddata.dto.VoucherDto;
import com.thinhnguyen.nab.service.prepaiddata.exception.ServiceRuntimeException;
import com.thinhnguyen.nab.service.prepaiddata.service.PrePaidDataService;
import com.thinhnguyen.nab.service.prepaiddata.tools.TimeRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

        //TODO: Wrap it
        TimeRunner<VoucherDto> subscriber = new TimeRunner(30);
        new Thread(() -> {
            VoucherDto result = service.payData(dataType);
            if(subscriber.isAlive()) {
                subscriber.receiveMessage(result);
                return;
            }
            if(result == null) {
                //call error
            }
            service.updateVoucher(result);
        }).start();
        subscriber.start();
        //Wait for thread to finished
        try {
            subscriber.join();
        } catch (InterruptedException e) {
            LOGGER.error("Thread was interrupted.");
            throw new ServiceRuntimeException("ERR02","Thread was interrupted.", e);
        }

        if(subscriber.isTimeout()) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        VoucherDto result = subscriber.getMessage();
        if(result == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        return ResponseEntity.ok(result);
    }
}
