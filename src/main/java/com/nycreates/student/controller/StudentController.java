package com.nycreates.student.controller;

import com.nycreates.student.annotation.SwaggerController;
import com.nycreates.student.enumerator.Crud;
import com.nycreates.student.exception.BadRequestException;
import com.nycreates.student.model.dto.StudentReadDto;
import com.nycreates.student.model.dto.StudentSaveDto;
import com.nycreates.student.model.filter.StudentFilter;
import com.nycreates.student.service.StudentService;
import com.nycreates.student.utility.PageUtil;
import com.nycreates.student.utility.ValidatorUtil;
import com.nycreates.student.validator.StudentValidator;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/student")
//@SwaggerController
public class StudentController {

  private final StudentService studentService;
  private final StudentValidator studentValidator;

  public StudentController(StudentService studentService, StudentValidator studentValidator) {
    this.studentService = studentService;
    this.studentValidator = studentValidator;
  }

  @PostMapping(value = "/create")
  @ResponseBody
  public StudentReadDto create(final @RequestBody StudentSaveDto request, final BindingResult errors)
      throws BindException {
    studentValidator.validate(Crud.CREATE, request, errors);
    return studentService.create(request);
  }

  // http://localhost:9999/student/new
  @GetMapping(value = "/new")
  public String retrieve(Model model) {
    StudentSaveDto student = new StudentSaveDto();
    model.addAttribute("student", student);
    return "newStudent";
  }

  // http://localhost:9999/student/retrieve/e856c3399d0b
  //@ResponseBody
  @GetMapping(value = "/retrieve/{id}")
  public String retrieve(
          @RequestParam(value="id", required = false) String id
          , Model model
  ) {
    StudentReadDto student = studentService.retrieve(id);
    model.addAttribute("student", student);
    return "editStudent"; //return "viewStudent";
  }


  @GetMapping(value = "/retrieve")
  @ResponseBody
  public List<StudentReadDto> retrieve(
      final @RequestParam(required = false) String id,
      final @RequestParam(required = false) String name,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Integer size,
      final HttpServletResponse httpResponse
  ) {
    if(id != null){
      studentValidator.validateId(id);
      return Collections.singletonList(studentService.retrieve(id));
    }
    else if(name != null){
      studentValidator.validateName(ValidatorUtil.removeControlCharacters(name));
      final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
      return studentService.retrieveByName(name, pageRequest, httpResponse);
    }
    else {
      throw new BadRequestException("'id' or 'name' is required!");
    }
  }

  @PostMapping(value = "/retrieve")
  @ResponseBody
  public List<StudentReadDto> retrieve(
      final @RequestBody StudentFilter filter,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Integer size,
      final HttpServletResponse httpResponse
  ) {
    final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
    return studentService.retrieve(filter, pageRequest, httpResponse);
  }

  @PatchMapping(value = "/update/{id}")
  @ResponseBody
  public StudentReadDto update(
      final @PathVariable String id,
      final @RequestBody StudentSaveDto request,
      final BindingResult errors
  ) throws BindException {
    studentValidator.validateId(id);
    studentValidator.validate(Crud.UPDATE, request, errors);
    return studentService.update(id, request);
  }

  @DeleteMapping(value = "/delete/{id}")
  @ResponseBody
  public boolean delete(final @PathVariable String id) {
    studentValidator.validateId(id);
    return studentService.delete(id);
  }
}
