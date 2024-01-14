package org.a2cd.boot;
import static org.junit.jupiter.api.Assertions.*;
import org.jasypt.util.text.AES256TextEncryptor;
import org.junit.jupiter.api.Test;

/**
 * @author a2cd
 * @since 2024-01-14
 */
class JasyptTest {
    @Test
    void encAndDecTest() {
        var plainText = "abcdef";
        var aesEncryptor = new AES256TextEncryptor();
        aesEncryptor.setPassword("bef57ec7f53a6d40beb640a780a639c83bc29ac8a9816f1fc6c5c6dcd93c4721");
        var encryptedText = aesEncryptor.encrypt(plainText);
        var decryptedText = aesEncryptor.decrypt(encryptedText);
        assertEquals(plainText, decryptedText);
    }

    @Test
    void enTest() {
        var plainText = "a2cd@Redis37255";
        var aesEncryptor = new AES256TextEncryptor();
        aesEncryptor.setPassword("7069a07715462d9da3d579c9e637acbc9622d9dcc698d374e53837a29e4a4af5");
        var encryptedText = aesEncryptor.encrypt(plainText);
        System.out.println(encryptedText);
        var decryptedText = aesEncryptor.decrypt(encryptedText);
        assertEquals(plainText, decryptedText);
    }
}
