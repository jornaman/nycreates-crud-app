package com.nycreates.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {

  public DataNotFoundException(final String message){
    super(String.format("DataNotFoundException | %s", message));
  }
}
