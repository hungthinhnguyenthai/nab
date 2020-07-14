package com.thinhnguyen.nab.service.prepaiddata.service;

import com.thinhnguyen.nab.service.prepaiddata.exception.ServiceRuntimeException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;

@Service
public class CipherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CipherService.class);


    @Value("${app.signature.rsa.private-key}")
    private Resource privateKeyResource;

    public String hmacSign(String content, String secret)  {
        Mac sha256HMAC = null;
        try {
            sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256HMAC.init(secret_key);
            return Base64.encodeBase64URLSafeString(sha256HMAC.doFinal(content.getBytes()));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            LOGGER.error("Cannot sign the token", e);
            throw new ServiceRuntimeException("ER07" ,"Cannot sign the token");
        }
    }

    public String decrypt(String cipherContent) {
        try {
            byte[] keyBytes = StreamUtils.copyToByteArray(privateKeyResource.getInputStream());
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = kf.generatePrivate(spec);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] cipherContentBytes = Base64.decodeBase64URLSafe(cipherContent);
            byte[] decryptedContent = cipher.doFinal(cipherContentBytes);
            String decoded = new String(decryptedContent);
            return decoded;
        } catch (Exception e) {
            LOGGER.error("Cannot decrypt the response", e);
            throw new ServiceRuntimeException("ER05" ,"Cannot decrypt the response");
        }

    }


}
