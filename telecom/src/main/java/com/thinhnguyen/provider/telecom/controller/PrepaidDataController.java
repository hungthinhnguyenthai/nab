package com.thinhnguyen.provider.telecom.controller;

import com.thinhnguyen.provider.telecom.dto.DataType;
import com.thinhnguyen.provider.telecom.dto.InvoiceDto;
import com.thinhnguyen.provider.telecom.dto.PrepaidDataDto;
import com.thinhnguyen.provider.telecom.service.PrepaidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@Validated
public class PrepaidDataController {

    @Autowired
    private PrepaidDataService dataService;

    @PostMapping(value = "/v1/data/{type}/purchase")
    public ResponseEntity<PrepaidDataDto> purchase(@PathVariable DataType type, @RequestBody InvoiceDto dto) throws IllegalBlockSizeException, NoSuchAlgorithmException, IOException, BadPaddingException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
        return ResponseEntity.ok(dataService.purchasePrepaidData(type, dto));
    }
}
