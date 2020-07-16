package com.thinhnguyen.provider.telecom.respository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VoucherResourceTest {

    @Autowired
    VoucherResource voucherResource;

    @Test
    public void show_me_the_voucher() {
        String pattern = "1";
        int len = 10;
        String voucher = voucherResource.generateVoucherCode(pattern, len);
        Assert.assertEquals(voucher,"1111111111");;
    }
}
