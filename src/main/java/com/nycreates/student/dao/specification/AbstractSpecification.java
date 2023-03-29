package com.nycreates.student.dao.specification;

import com.nycreates.student.model.filter.DateFilter;
import com.nycreates.student.utility.ValidatorUtil;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public abstract class AbstractSpecification<T> implements Specification<T> {

  private CriteriaBuilder criteriaBuilder;
  private List<Predicate> predicates;

  @Override
  public Predicate toPredicate(
      Root<T> root,
      CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder
  ) {
    this.criteriaBuilder = criteriaBuilder;
    this.predicates = new ArrayList<>();

    doFilter(root, criteriaQuery, criteriaBuilder);

    return criteriaQuery
        .distinct(true)
        .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])))
        .getRestriction();
  }

  abstract void doFilter(
      Root<T> root,
      CriteriaQuery<?> criteriaQuery,
      CriteriaBuilder criteriaBuilder
  );

  protected void applyDateTimeFilter(
      final Expression<LocalDateTime> path,
      final DateFilter<LocalDateTime> value
  ){
    if(ValidatorUtil.isInvalid(value)){
      return;
    }
    final LocalDateTime now = LocalDateTime.now();
    final LocalDateTime start = value.getStartOrDefault(now.minus(1, ChronoUnit.CENTURIES));
    final LocalDateTime until = value.getUntilOrDefault(now);

    if(ValidatorUtil.isValid(start)) {
      predicates.add(criteriaBuilder.between(path, start, until));
    }
  }

  protected <X, Y extends Collection<X>> void applyInFilter(
      final Expression<X> path,
      final Y values
  ){
    if(ValidatorUtil.isInvalid(values)){
      return;
    }
    predicates.add(path.in(values));
  }

  protected  <X extends Collection<String>> void applyStringFilter(
      final Expression<String> path,
      final X values
  ){
    if(ValidatorUtil.isInvalid(values)){
      return;
    }
    final Expression<String> targetPath = criteriaBuilder.lower(path);
    final List<String> inClause = new ArrayList<>();

    for(String s : values){
      final boolean isLike = isLike(s);
      final boolean isNot = isNot(s);

      if(!isLike && !isNot){
        inClause.add(s.toLowerCase());
        continue;
      }
      final String targetValue = (isNot ? s.substring(1) : s).toLowerCase();
      predicates.add(createPredicate(targetPath, targetValue, isLike, isNot));
    }
    if(!inClause.isEmpty()){
      applyInFilter(targetPath, inClause);
    }
  }

  protected void applyStringFilterToFields(Collection<Expression<String>> fields, String value) {
    if(ValidatorUtil.isInvalid(value)){
      return;
    }
    final boolean isLike = isLike(value);
    final boolean isNot = isNot(value);

    final String targetValue = (isNot ? value.substring(1) : value).toLowerCase();
    Predicate[] filterPredicates = fields.stream().map(field -> createPredicate(field, targetValue, isLike, isNot)).toArray(Predicate[]::new);

    if(isNot){
      predicates.add(criteriaBuilder.and(filterPredicates));
    } else {
      predicates.add(criteriaBuilder.or(filterPredicates));
    }
  }

  private boolean isNot(String s) {
    return s.startsWith("!");
  }

  private boolean isLike(String s) {
    return s.contains("%") || s.contains("*");
  }

  private Predicate createPredicate(Expression<String> path, String targetValue, boolean isLike, boolean isNot) {
    Predicate predicate;

    Expression<String> targetPath = criteriaBuilder.lower(path);
    if(isLike){
      predicate = criteriaBuilder.like(targetPath, targetValue.replace("*", "%"));
    } else {
      predicate = criteriaBuilder.equal(targetPath, targetValue);
    }

    if(isNot){
      predicate = criteriaBuilder.not(predicate);
    }
    return predicate;
  }
}
