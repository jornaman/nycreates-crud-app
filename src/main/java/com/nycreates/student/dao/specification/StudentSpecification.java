package com.nycreates.student.dao.specification;

import com.nycreates.student.dao.model.Student;
import com.nycreates.student.model.filter.StudentFilter;

import javax.persistence.criteria.*;

public class StudentSpecification extends AbstractSpecification<Student> {

  private final StudentFilter filter;

  public StudentSpecification(final StudentFilter filter){
    this.filter = filter;
  }

  @Override
  public void doFilter(
      Root<Student> root,
      CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder
  ){
    applyDateTimeFilter(root.get("updated"), filter.getDateFilter());

    applyInFilter(root.get("id"), filter.getIds());

    applyStringFilter(root.get("firstName"), filter.getFirstNames());
    applyStringFilter(root.get("lastName"), filter.getLastNames());
    applyStringFilter(root.get("middleName"), filter.getMiddleNames());
  }
}
