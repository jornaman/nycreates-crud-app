package com.nycreates.student.controller;

import com.nycreates.student.annotation.SwaggerController;
import com.nycreates.student.model.dto.LoginDto;
import com.nycreates.student.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/login")
@SwaggerController
public class LoginController {

  private final LoginService loginService;

  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping
  public void login(final @RequestBody LoginDto request, HttpServletResponse httpResponse){
    if(request.getUsername() == null){
      throw new IllegalArgumentException("'username' is required");
    }
    if(request.getPassword() == null){
      throw new IllegalArgumentException("'password' is required");
    }
    loginService.login(request);
    httpResponse.setStatus(204);
  }
}
