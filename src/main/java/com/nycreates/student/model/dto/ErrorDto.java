package com.nycreates.student.model.dto;

import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDto {

  private Set<String> errors;
  private Class<?> exception;
  private String message;
  private Integer statusCode;
}
