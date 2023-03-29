package com.nycreates.student.dao.model.mapper;

import com.nycreates.student.dao.model.Student;
import com.nycreates.student.model.dto.StudentReadDto;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {

  private final MapperFacade mapperFacade;

  public ResourceMapper(MapperFacade mapperFacade) {
    this.mapperFacade = mapperFacade;
  }

  public <T extends Collection<Student>> List<StudentReadDto> convertStudents(final T students){
    return students.stream().map(this::convertStudent).collect(Collectors.toList());
  }

  public StudentReadDto convertStudent(final Student student){
    final StudentReadDto dto = mapperFacade.map(student, StudentReadDto.class);
    dto.setName(
        String.format("%s%s %s",
            student.getFirstName(),
            student.getMiddleName() != null ? String.format(" %s", student.getMiddleName()) : "",
            student.getLastName()
        )
    );
    dto.setPhoneNumber(student.getPhoneNumber());
    dto.setEmailAddress(student.getEmailAddress());
    return dto;
  }
}
