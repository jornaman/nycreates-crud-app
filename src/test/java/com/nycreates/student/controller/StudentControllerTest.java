package com.nycreates.student.controller;

import com.nycreates.student.App;
import com.nycreates.student.model.dto.StudentReadDto;
import com.nycreates.student.service.StudentService;
import com.nycreates.student.utility.PageUtil;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {App.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService studentService;

  private List<StudentReadDto> students;

  @BeforeEach
  public void beforeEach(){
    final List<Integer> ids = IntStream
        .range(1, 10)
        .boxed()
        .collect(Collectors.toList());

    this.students = ids.stream()
        .map(id -> createStudentReadDto(Integer.toString(id)))
        .collect(Collectors.toList());
  }

  @Test
  void getRetrieveWithName() throws Exception {
    when(studentService
        .retrieveByName(anyString(), any(PageRequest.class), any(HttpServletResponse.class)))
        .thenReturn(students);

    final ResultActions result = this.mockMvc
        .perform(get("/student/retrieve?name=lucy"))
        .andExpect(status().isOk());

    for(int i = 0; i < students.size(); i++){
      result.andExpect(jsonPath(String.format("$[%d].id", i)).value(students.get(i).getId()));
    }

    final PageRequest pageRequest = PageUtil.createPageRequest(null, null);
    verify(studentService).retrieveByName(
        eq("lucy"), eq(pageRequest), any(HttpServletResponse.class)
    );
  }

  @Test
  void getRetrieveWithNameEmpty() throws Exception {
    this.mockMvc.perform(get("/student/retrieve?name="))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getRetrieveWithNameSpecialCharacters() throws Exception {
    this.mockMvc.perform(get("/student/retrieve?name=^"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getRetrieveWithNameSpace() throws Exception {
    this.mockMvc.perform(get("/student/retrieve?name=Sa w"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getRetrieveWithNameMissing() throws Exception {
    this.mockMvc.perform(get("/student/retrieve"))
        .andExpect(status().isBadRequest());
  }

  private StudentReadDto createStudentReadDto(final String id){
    final StudentReadDto user = new StudentReadDto();
    user.setId(id);
    return user;
  }
}