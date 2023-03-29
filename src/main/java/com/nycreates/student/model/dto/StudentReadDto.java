package com.nycreates.student.model.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StudentReadDto {

  private String id;
  private String name;
  private String firstName;
  private String middleName;
  private String lastName;
  private String phoneNumber;
  private String emailAddress;
  private LocalDateTime updated;
}
