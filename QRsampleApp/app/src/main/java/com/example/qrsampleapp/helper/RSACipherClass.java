package com.example.qrsampleapp.helper;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSACipherClass {


    /**
     * 暗号化
     * @param plain
     * @param publicKey
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     */
    public static String encryptionByPuclicPey(String plain, RSAPublicKey publicKey)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        // 公開鍵で暗号化
        Cipher encrypterWithPublicKey = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        encrypterWithPublicKey.init(Cipher.ENCRYPT_MODE, publicKey);
        System.out.println(plain.getBytes().length);
        byte[] encryptedWithPublicKey = encrypterWithPublicKey.doFinal(plain.getBytes());
        String cryptogram = new String(Base64.encode(encryptedWithPublicKey, encryptedWithPublicKey.length));
        return cryptogram;
    }


    /**
     * 復号化
     * @param cryptogram
     * @param privateKey
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws UnsupportedEncodingException
     */
    public static String decryptionByPrivateKey(String cryptogram, RSAPrivateKey privateKey)
            throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
        //秘密鍵で復号
        Cipher decrypterWithPriavteKey = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        decrypterWithPriavteKey.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decode_cryptogram = Base64.decode(cryptogram, Base64.DEFAULT);
        byte[] decryptedWithPriavteKey = decrypterWithPriavteKey.doFinal(decode_cryptogram);
        String plain = new String(decryptedWithPriavteKey, "UTF-8");
        System.out.println("cryptogram :" + plain);
        return plain;
    }
}
