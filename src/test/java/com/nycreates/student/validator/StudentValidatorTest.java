package com.nycreates.student.validator;

import com.nycreates.student.App;
import com.nycreates.student.MapperArgConverter;
import com.nycreates.student.enumerator.Crud;
import com.nycreates.student.model.dto.StudentSaveDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StudentValidatorTest {

  @Autowired
  private StudentValidator userValidator;

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/UserValidatorTest/validateCreateTests.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  public void validateCreate(
      @ConvertWith(MapperArgConverter.class) StudentSaveDto userSaveDto,
      boolean isValid
  ) {
    final Executable executable = () -> userValidator.validate(
        Crud.CREATE, userSaveDto, createBindingResult(userSaveDto)
    );
    if (isValid) {
      assertDoesNotThrow(executable);
    } else {
      assertThrows(BindException.class, executable);
    }
  }

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/UserValidatorTest/validateUpdateTests.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
  public void validateUpdate(
      @ConvertWith(MapperArgConverter.class) StudentSaveDto userSaveDto,
      boolean isValid
  ) {
    final Executable executable = () -> userValidator.validate(
        Crud.UPDATE, userSaveDto, createBindingResult(userSaveDto)
    );
    if (isValid) {
      assertDoesNotThrow(executable);
    } else {
      assertThrows(BindException.class, executable);
    }
  }

  private BeanPropertyBindingResult createBindingResult(final StudentSaveDto userSaveDto){
    return new BeanPropertyBindingResult(userSaveDto, "userSaveDto");
  }
}