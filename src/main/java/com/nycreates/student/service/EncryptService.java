package com.nycreates.student.service;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EncryptService {

  private static final Logger LOGGER = LoggerFactory.getLogger(EncryptService.class);

  private final String secretKey;
  private SecretKeySpec secretKeySpec;

  public EncryptService(String secretKey) {
    this.secretKey = secretKey;
    configureKey();
  }

  public String encrypt(final String value){
    if(value == null){
      throw new IllegalArgumentException("'value' is invalid");
    }
    final byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
    try {
      final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
      return Base64.getEncoder().encodeToString(cipher.doFinal(bytes));
    }
    catch(BadPaddingException
        | IllegalBlockSizeException
        | InvalidKeyException
        | NoSuchAlgorithmException
        | NoSuchPaddingException e
    ){
      LOGGER.error("encrypt() error", e);
      throw new IllegalStateException("encryption error");
    }
  }

  private void configureKey(){
    try {
      final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
      byte[] key = secretKey.getBytes(StandardCharsets.UTF_8);
      key = messageDigest.digest(key);
      key = Arrays.copyOf(key, 16);
      secretKeySpec = new SecretKeySpec(key, "AES");
    }
    catch(NoSuchAlgorithmException e){
      LOGGER.error("configureKey() error", e);
    }
  }
}
