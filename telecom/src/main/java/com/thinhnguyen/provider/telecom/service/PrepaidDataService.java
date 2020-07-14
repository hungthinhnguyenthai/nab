package com.thinhnguyen.provider.telecom.service;

import com.thinhnguyen.provider.telecom.dto.DataType;
import com.thinhnguyen.provider.telecom.dto.InvoiceDto;
import com.thinhnguyen.provider.telecom.dto.PrepaidDataDto;
import com.thinhnguyen.provider.telecom.respository.VoucherResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.ZonedDateTime;

@Service
public class PrepaidDataService {

    @Value("${app.voucher.length}")
    private Integer voucherLen;

    @Value("${app.signature.rsa.client.public-key}")
    private Resource publicKeyResource;

    @Autowired
    private VoucherResource resource;

    @Autowired
    private CipherService cipherService;



    public static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String DIGITS = "0123456789";
    public static final String PUNCTUATION = "!@#$%&*()_+-=[]|,./?><";


    /**
     * Store the invoice and retrieve voucher code.
     *
     * @param invoiceDto
     * @return the voucher code and the expiration information
     */
    public PrepaidDataDto purchasePrepaidData(DataType dataType, InvoiceDto invoiceDto) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
        PrepaidDataDto prepaidDataDto = new PrepaidDataDto();

        String voucherCode = resource.generateVoucherCode(DIGITS, voucherLen);
        InputStream inputStream =  publicKeyResource.getInputStream();
        prepaidDataDto.setVoucherCode(cipherService.encrypt(voucherCode, inputStream));
        ZonedDateTime now = ZonedDateTime.now();
        prepaidDataDto.setExpiredDate(now.plusDays(dataType.getDays()));

        //TODO Store invoice and voucher code information
        return prepaidDataDto;
    }

}
