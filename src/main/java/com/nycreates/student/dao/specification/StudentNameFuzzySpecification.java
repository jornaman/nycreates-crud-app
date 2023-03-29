package com.nycreates.student.dao.specification;

import com.nycreates.student.dao.model.Student;
import com.nycreates.student.model.filter.StudentNameFuzzyFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Set;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class StudentNameFuzzySpecification extends AbstractSpecification<Student> {

  private final StudentNameFuzzyFilter filter;

  public StudentNameFuzzySpecification(final StudentNameFuzzyFilter filter) {
    this.filter = filter;
  }

  @Override
  public void doFilter(
      Root<Student> root,
      CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder
  ) {
    applyStringFilterToFields(Set.of(
        root.get("firstName"),
        root.get("lastName"),
        root.get("middleName")
    ), filter.getName());
  }

}
