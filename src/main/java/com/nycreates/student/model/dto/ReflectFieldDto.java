package com.nycreates.student.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ReflectFieldDto<T> {

  private final Class<T> type;
  private String field;
  private T value;

  public ReflectFieldDto(final Class<T> type){
    super();
    this.type = type;
  }

  public void setValue(Object obj){
    this.value = type.cast(obj);
  }
}
