package com.thinhnguyen.provider.telecom;

import com.thinhnguyen.provider.telecom.respository.VoucherResource;
import com.thinhnguyen.provider.telecom.service.CipherService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static com.thinhnguyen.provider.telecom.service.PrepaidDataService.DIGITS;

@RunWith(SpringRunner.class)
@SpringBootTest
class TelecomApplicationTests {


    @Value("classpath:static/private_key.der")
    private Resource privateKeyResource;

    @Value("classpath:static/public_key.der")
    private Resource publicKeyResource;

    @Autowired
    private VoucherResource resource;

    @Autowired
    private CipherService cipherService;

    @Test
    void testCipher() throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        String voucherCode = resource.generateVoucherCode(DIGITS, 14);
        String encrytedVoucher = cipherService.encrypt(voucherCode, Files.newInputStream(publicKeyResource.getFile().toPath()));
        String decryptVoucher = cipherService.decrypt(encrytedVoucher, privateKeyResource.getFile());
        Assert.assertEquals(voucherCode, decryptVoucher);
    }

}
