package com.nycreates.student.service;

import com.nycreates.student.model.dto.StudentReadDto;
import com.nycreates.student.model.dto.StudentSaveDto;
import com.nycreates.student.model.filter.StudentFilter;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.PageRequest;

public interface StudentService {

  public StudentReadDto create(final StudentSaveDto request);
  public StudentReadDto retrieve(final String id);
  public List <StudentReadDto> retrieve();

  public List <StudentReadDto> retrieveByName(
      final String name,
      final PageRequest pageRequest,
      final HttpServletResponse httpResponse
  );

  public List<StudentReadDto> retrieve(
      final StudentFilter filter,
      final PageRequest pageRequest,
      final HttpServletResponse httpResponse
  );

  public StudentReadDto update(final String id, final StudentSaveDto request);

  public boolean delete(final String id);
}
