package com.nycreates.student.model.filter;

import java.time.temporal.Temporal;
import lombok.Data;

@Data
public class DateFilter<T extends Temporal> {

  private T start;
  private T until;

  public T getStartOrDefault(T defaultValue){
    return start != null ? start : defaultValue;
  }

  public T getUntilOrDefault(T defaultValue){
    return until != null ? until : defaultValue;
  }
}
