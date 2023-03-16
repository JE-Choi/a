package com.jechoi.a.cipher;

import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * https://devjjsjjj.tistory.com/entry/Cipher-%EC%9E%90%EB%B0%94%EC%9D%98-%EC%95%94%ED%98%B8%ED%99%94%EB%B3%B5%ED%98%B8%ED%99%94%EB%A5%BC-%EB%8B%B4%EB%8B%B9%ED%95%98%EB%8A%94-%ED%81%B4%EB%9E%98%EC%8A%A4-1
 */
class EncryptorTest {
    @Test
    public void 암호화하고복호화하면_복호하된것은원본과같다() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        String encryptionKeyString = "encryptionKey";
        String originalMessage = "암호화 메시지입니다.";
        byte[] encryptionKeyBytes = encryptionKeyString.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(encryptionKeyBytes, "AES");

        // 암호화
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrpyedMessageBytes = cipher.doFinal(originalMessage.getBytes());

        // 복호화
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedMessageBytes = cipher.doFinal(encrpyedMessageBytes);

        assert (originalMessage).equals(new String(decryptedMessageBytes));
    }
}