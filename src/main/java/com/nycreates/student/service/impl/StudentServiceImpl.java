package com.nycreates.student.service.impl;

import com.nycreates.student.service.StudentService;
import com.nycreates.student.dao.model.Student;
import com.nycreates.student.dao.model.mapper.ResourceMapper;
import com.nycreates.student.dao.repository.StudentRepository;
import com.nycreates.student.dao.specification.StudentNameFuzzySpecification;
import com.nycreates.student.dao.specification.StudentSpecification;
import com.nycreates.student.exception.DataNotFoundException;
import com.nycreates.student.model.dto.StudentReadDto;
import com.nycreates.student.model.dto.StudentSaveDto;
import com.nycreates.student.model.filter.StudentFilter;
import com.nycreates.student.model.filter.StudentNameFuzzyFilter;
import com.nycreates.student.utility.PageUtil;
import com.nycreates.student.utility.ValidatorUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.nycreates.student.service.StudentService.class);

    private final StudentRepository studentRepository;
    private final ResourceMapper resourceMapper;

    public StudentServiceImpl(StudentRepository studentRepository, ResourceMapper resourceMapper) {
        this.studentRepository = studentRepository;
        this.resourceMapper = resourceMapper;
    }

    public StudentReadDto create(final StudentSaveDto request){
        Student student = studentRepository.save(new Student(request));

        LOGGER.info("created student '{}'", student.getId());
        return resourceMapper.convertStudent(student);
    }

    public StudentReadDto retrieve(final String id){
        Optional<Student> optional = studentRepository.findById(id);
        Student student = getStudent(id, optional);

        LOGGER.info("found user '{}'", id);
        return resourceMapper.convertStudent(student);
    }

    public List<StudentReadDto> retrieve(){
        final List<Student> studentsDaos = studentRepository.findAll();
        final List<StudentReadDto> students = resourceMapper.convertStudents(studentsDaos);
        LOGGER.info("found {} student(s)", students.size());
        return students;
    }

    public List <StudentReadDto> retrieveByName(
            final String name,
            final PageRequest pageRequest,
            final HttpServletResponse httpResponse
    ){
        final StudentNameFuzzySpecification specification = new StudentNameFuzzySpecification(StudentNameFuzzyFilter.builder()
                .name(name)
                .build());
        final Page<Student> studentPage = studentRepository.findAll(specification, pageRequest);
        final List<StudentReadDto> students = resourceMapper.convertStudents(studentPage.getContent());
        LOGGER.info("found {} student(s)", students.size());

        PageUtil.updatePageHeaders(httpResponse, studentPage, pageRequest);
        return students;
    }

    public List<StudentReadDto> retrieve(
            final StudentFilter filter,
            final PageRequest pageRequest,
            final HttpServletResponse httpResponse
    ){
        final StudentSpecification specification = new StudentSpecification(filter);
        final Page<Student> studentPage = studentRepository.findAll(specification, pageRequest);
        final List<StudentReadDto> students = resourceMapper.convertStudents(studentPage.getContent());
        LOGGER.info("found {} student(s)", students.size());

        PageUtil.updatePageHeaders(httpResponse, studentPage, pageRequest);
        return students;
    }

    public StudentReadDto update(final String id, final StudentSaveDto request){

        final Optional<Student> optional = studentRepository.findById(id);
        final Student student = getStudent(id, optional);
        student.setUpdated(LocalDateTime.now());

        if(ValidatorUtil.isValid(request.getFirstName())){
            student.setFirstName(request.getFirstName());
        }
        if(ValidatorUtil.isValid(request.getMiddleName())){
            student.setMiddleName(request.getMiddleName());
        }
        if(ValidatorUtil.isValid(request.getLastName())){
            student.setLastName(request.getLastName());
        }
        if(ValidatorUtil.isValid(request.getPhoneNumber())){
            student.setPhoneNumber(request.getPhoneNumber());
        }

        final Student updated = studentRepository.save(student);

        LOGGER.info("updated student '{}'", updated.getId());
        return resourceMapper.convertStudent(updated);
    }

    public boolean delete(final String id){

        final Optional<Student> optional = studentRepository.findById(id);
        final Student student = getStudent(id, optional);

        studentRepository.deleteById(student.getId());

        final boolean isDeleted = studentRepository.findById(id).isEmpty();
        if(isDeleted){
            LOGGER.info("deleted student '{}'", id);
        } else {
            LOGGER.warn("failed to delete student '{}'", id);
        }
        return isDeleted;
    }

    private Student getStudent(final String id, final Optional<Student> student){

        if(student.isEmpty()){
            final String message = String.format("student '%s' doesn't exist", id);
            LOGGER.warn(message);
            throw new DataNotFoundException(message);
        }
        return student.get();
    }
}
