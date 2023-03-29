package com.nycreates.student.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletResponse;

import com.nycreates.student.dao.model.Student;
import com.nycreates.student.dao.model.mapper.ResourceMapper;
import com.nycreates.student.dao.repository.StudentRepository;
import com.nycreates.student.utility.PageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nycreates.student.App;
import com.nycreates.student.dao.specification.StudentNameFuzzySpecification;
import com.nycreates.student.model.dto.StudentReadDto;
import com.nycreates.student.model.filter.StudentNameFuzzyFilter;
import javax.servlet.http.HttpServletResponse;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StudentServiceTest {

  @Autowired
  private HttpServletResponse httpResponse;

  @Autowired
  private StudentService studentService;

  @MockBean
  private ResourceMapper resourceMapper;

  @MockBean
  private StudentRepository studentRepository;

  private List<StudentReadDto> dtoStudents;

  @BeforeEach
  public void beforeEach(){
    final List<Integer> ids = IntStream
        .range(1, 10)
        .boxed()
        .collect(Collectors.toList());

    final Page<Student> mockPage = mock(Page.class);
    when(studentRepository.findAll(any(StudentNameFuzzySpecification.class), any(PageRequest.class)))
        .thenReturn(mockPage);

    final List<Student> daoStudents = ids.stream()
        .map(id -> createStudent(Integer.toString(id)))
        .collect(Collectors.toList());
    when(mockPage.getContent()).thenReturn(daoStudents);
    when(mockPage.getTotalPages()).thenReturn(9);

    this.dtoStudents = ids.stream()
        .map(id -> createStudentReadDto(Integer.toString(id)))
        .collect(Collectors.toList());
    when(resourceMapper.convertStudents(daoStudents)).thenReturn(dtoStudents);
  }

  @Test
  public void retrieveByName() {
    final String input = "input";
    final PageRequest pageRequest = PageUtil.createPageRequest(null, null);
    final List<StudentReadDto> results = studentService.retrieveByName(input, pageRequest, httpResponse);
    assertEquals(dtoStudents, results);
	//assertEquals("9", httpResponse.getHeader("page-count"));
	//assertEquals("1", httpResponse.getHeader("page-number"));
	//assertEquals("20", httpResponse.getHeader("page-size"));

    final StudentNameFuzzyFilter filter = new StudentNameFuzzyFilter(input);
    final StudentNameFuzzySpecification specification = new StudentNameFuzzySpecification(filter);
    verify(studentRepository).findAll(specification, pageRequest);
  }

  @Test
  public void retrieveByNamePaged() {
    final String input = "input2";
    final PageRequest pageRequest = PageUtil.createPageRequest(2, 5);
    final List<StudentReadDto> results = studentService.retrieveByName(input, pageRequest, httpResponse);
    assertEquals(dtoStudents, results);
	//assertEquals("9", httpResponse.getHeader("page-count"));
	//assertEquals("2", httpResponse.getHeader("page-number"));
	//assertEquals("5", httpResponse.getHeader("page-size"));

    final StudentNameFuzzyFilter filter = new StudentNameFuzzyFilter(input);
    final StudentNameFuzzySpecification specification = new StudentNameFuzzySpecification(filter);
    verify(studentRepository).findAll(specification, pageRequest);
  }

  private Student createStudent(final String id){
    final Student student = new Student();
    student.setId(id);
    return student;
  }

  private StudentReadDto createStudentReadDto(final String id){
    final StudentReadDto student = new StudentReadDto();
    student.setId(id);
    return student;
  }
}