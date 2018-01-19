package com.cloud.user.microservice.utils;

import com.cloud.common.encrypt.EncryptUtil;
import com.cloud.common.encrypt.RSAEncrypt;
import com.cloud.user.microservice.UserMicroserviceApplicationTests;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSAEncryptTest extends UserMicroserviceApplicationTests {


    @Test
    public void testGenKeyPair(){
        RSAEncrypt.genKeyPair("E:/");
    }

    @Test
    public void testLoadPublicKey() throws Exception{
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("publicKey.keystore");
        InputStreamReader reader = new InputStreamReader(inputStream);
        Assert.assertNotNull(RSAEncrypt.loadKeyByFile(reader));
    }

    @Test
    public void testEncrypt() throws Exception{
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("publicKey.keystore");
        InputStreamReader reader = new InputStreamReader(inputStream);
        String keyStr = RSAEncrypt.loadKeyByFile(reader);
        RSAPublicKey publicKey = RSAEncrypt.loadPublicKeyByStr(keyStr);
        byte[] encrypt = RSAEncrypt.encrypt(publicKey,"123456".getBytes());

        System.out.println(new java.lang.String(encrypt,"utf-8"));
        byte[] pass = "fTBbHT6Uq+tNAkR43xkua9pfyJzUIds52XlAJ2CX7tKfwIPMl52pvCJ0e4r+TxYPNqxCL3ki2dek2ndLoj4PFdPOjrUBvufi3ZAmkhS3jOyJhg08+5kGN+9q8LI6FVTsqkMjn6O0dEuebVx94ivBJOS5XFmRyCOUMp2Z0U9z5lk=".getBytes();

        InputStream privateStream = this.getClass().getClassLoader().getResourceAsStream("publicKey.keystore");
        InputStreamReader privateReader = new InputStreamReader(privateStream);
        String priveteKeyStr = RSAEncrypt.loadKeyByFile(privateReader);
        RSAPrivateKey privateKey = RSAEncrypt.loadPrivateKeyByStr(priveteKeyStr);
        byte[] dencrypt = RSAEncrypt.decrypt(privateKey,pass);
        Assert.assertEquals("123456",new String(dencrypt));
    }

    @Test
    public void testCommonEncrypt(){
        System.out.println(EncryptUtil.encryptSha512("123456test"));
    }


}
