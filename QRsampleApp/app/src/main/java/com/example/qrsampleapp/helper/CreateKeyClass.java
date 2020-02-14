package com.example.qrsampleapp.helper;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class CreateKeyClass {

    KeyFactory factoty;
    RSAPublicKeySpec publicKeySpec;
    RSAPrivateKeySpec privateKeySpec;


    public CreateKeyClass() throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 公開鍵・秘密鍵を生成する。
        KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");
        kg.initialize(1024);
        KeyPair keyPair = kg.generateKeyPair();
        this.factoty = KeyFactory.getInstance("RSA");
        this.publicKeySpec = factoty.getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
        this.privateKeySpec = factoty.getKeySpec(keyPair.getPrivate(), RSAPrivateKeySpec.class);
    }

    /**
     * 公開鍵を生成
     * @return PublicKey
     * @throws InvalidKeySpecException
     */
    public RSAPublicKey createPublicKey() throws InvalidKeySpecException {
        RSAPublicKey publicKey = (RSAPublicKey) factoty.generatePublic(this.publicKeySpec);
        return publicKey;
    }

    /**
     * 秘密鍵を生成
     * @return PrivateKey
     * @throws InvalidKeySpecException
     */
    public RSAPrivateKey createPrivateKey() throws InvalidKeySpecException {
        RSAPrivateKey privateKey = (RSAPrivateKey) factoty.generatePrivate(this.privateKeySpec);
        return privateKey;
    }

}
