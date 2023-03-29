package com.nycreates.student.service;

import com.nycreates.student.exception.ForbiddenException;
import com.nycreates.student.model.dto.LoginDto;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final EncryptService encryptService;

  public LoginService(EncryptService encryptService) {
    this.encryptService = encryptService;
  }

  public void login(final LoginDto request){
    final String encryptedPassword = encryptService.encrypt(request.getPassword());

    //if(profile == null || !profile.getPassword().equals(encryptedPassword)){
    //  throw new ForbiddenException("'username' or 'password' is invalid");
    //}
  }
}
