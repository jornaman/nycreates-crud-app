package com.nycreates.student.validator;

import com.nycreates.student.enumerator.Crud;
import java.util.Collection;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public abstract class CrudValidator implements Validator {

  private Crud method;

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  @Override
  public void validate(Object o, Errors errors) {

    if(o instanceof Collection<?>){
      ((Collection<?>)o).forEach(i -> validate(i, errors));
      return;
    }
    switch(method){
      case CREATE:
        validateCreate(o, errors);
        return;
      case RETRIEVE:
        validateRetrieve(o, errors);
        return;
      case UPDATE:
        validateUpdate(o, errors);
        return;
      case DELETE:
        validateDelete(o, errors);
        return;
      default:
        throw new UnsupportedOperationException("unknown method specified");
    }
  }

  public void validate(final Crud method, final Object instance, final BindingResult errors)
      throws BindException {

    this.method = method;
    ValidationUtils.invokeValidator(this, instance, errors);

    this.method = null;
    if(errors.hasErrors()){
      throw new BindException(errors);
    }
  }

  protected void validateCreate(final Object o, final Errors errors){
    throw new UnsupportedOperationException("create is not supported");
  }

  protected void validateRetrieve(final Object o, final Errors errors){
    throw new UnsupportedOperationException("retrieve is not supported");
  }

  protected void validateUpdate(final Object o, final Errors errors){
    throw new UnsupportedOperationException("update is not supported");
  }

  protected void validateDelete(final Object o, final Errors errors){
    throw new UnsupportedOperationException("delete is not supported");
  }
}
