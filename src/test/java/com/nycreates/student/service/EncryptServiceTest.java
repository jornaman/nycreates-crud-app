package com.nycreates.student.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EncryptServiceTest {

  private EncryptService encryptService;

  @BeforeEach
  public void beforeEach(){
    this.encryptService = null;
  }

  @Test
  public void encrypt(){
    setEncryptService();
    final String encrypted = this.encryptService.encrypt("password");
    Assertions.assertEquals("doSiQvzG31cjkMBu+QcN1w==", encrypted);
  }

  @Test
  public void encryptWithDifferentSecret(){
    setEncryptService("$DifferentSecret9");
    final String encrypted = this.encryptService.encrypt("password");
    Assertions.assertEquals("gGuJA4Ihv0g7tP1Ej9gjJQ==", encrypted);
  }

  @Test
  public void encryptWithDifferentValue(){
    setEncryptService();
    final String encrypted = this.encryptService.encrypt("DifferentValue");
    Assertions.assertEquals("UtX2kBisxCzKXItumXzABQ==", encrypted);
  }

  @Test
  public void encryptInvalidInput(){
    setEncryptService();
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> this.encryptService.encrypt(null)
    );
  }

  private void setEncryptService(){
    setEncryptService("secret123");
  }

  private void setEncryptService(final String secretKey){
    this.encryptService = new EncryptService(secretKey);
  }
}
