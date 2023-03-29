package com.nycreates.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

  public ForbiddenException(final String message){
    super(String.format("Forbidden | %s", message));
  }
}
