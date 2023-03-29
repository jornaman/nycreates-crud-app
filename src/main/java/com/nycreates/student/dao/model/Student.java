package com.nycreates.student.dao.model;

import com.nycreates.student.model.dto.StudentSaveDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;

import lombok.Data;

@Entity
@Data
@Table(name = "student")
public class Student {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "middle_name")
  private String middleName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @Column(name = "email_address", nullable = false)
  private String emailAddress;

  @Column(name = "updated", nullable = false)
  private LocalDateTime updated;

  public Student(){
    super();
    this.id = UUID.randomUUID().toString();
    this.updated = LocalDateTime.now();
  }

  public Student(final StudentSaveDto student){
    this();
    this.firstName = student.getFirstName();
    this.middleName = student.getMiddleName();
    this.lastName = student.getLastName();
    this.phoneNumber = student.getPhoneNumber();
    this.emailAddress = student.getEmailAddress();
  }

}
