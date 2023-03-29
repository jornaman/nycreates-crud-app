package com.nycreates.student.dao.model.mapper;

import com.nycreates.student.App;
import com.nycreates.student.MapperArgConverter;
import com.nycreates.student.dao.model.Student;
import com.nycreates.student.model.dto.StudentReadDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ResourceMapperTest {

  @Autowired
  private ResourceMapper resourceMapper;

  @ParameterizedTest
  @CsvFileSource(
      resources = ("/ResourceMapperTest/convertStudentTest.csv"),
      delimiter = '$',
      lineSeparator = ">"
  )
	void convertStudentTest(
      @ConvertWith(MapperArgConverter.class) Student student,
      @ConvertWith(MapperArgConverter.class) StudentReadDto studentReadDto
  ) {
    Assertions.assertEquals(studentReadDto, resourceMapper.convertStudent(student));
  }

}