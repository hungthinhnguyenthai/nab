package com.thinhnguyen.provider.telecom.respository;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class VoucherResource {

    private Random random = new Random();

    /**
     * Create voucher code with provided series and len
     * @param series
     * @param len
     * @return
     */
    public String generateVoucherCode(String series, int len) {
        char[] token = new char[len];
        for (int i = 0; i < len; i++) {
            token[i] = series.charAt(random.nextInt(series.length()));
        }
        return new String(token);
    }
}
