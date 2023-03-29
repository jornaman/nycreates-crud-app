package com.nycreates.student.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentSaveDto {

  private String firstName;
  private String middleName;
  private String lastName;
  private String phoneNumber;
  private String emailAddress;

}
