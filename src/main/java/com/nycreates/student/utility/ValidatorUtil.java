package com.nycreates.student.utility;

import com.nycreates.student.model.dto.ReflectClassDto;
import com.nycreates.student.model.dto.ReflectFieldDto;
import com.nycreates.student.model.filter.DateFilter;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidatorUtil {

  public static <T> boolean isEmpty(T value, String... excludeFields){

    final ReflectClassDto reflection = ReflectUtil.describe(value);
    if(reflection == null){
      throw new IllegalStateException(String.format("could not describe class: '%s'", value));
    }
    reflection.removeFieldsByNames(excludeFields);
    for(ReflectFieldDto<?> field : reflection.getFields()){
      if(isValid(field.getValue())){
        return false;
      }
    }
    return true;
  }

  public static <T> boolean isInvalid(T value){
    return !isValid(value);
  }

  public static <T> boolean isValid(T value){

    boolean isValid;
    if(value instanceof Collection<?>){
      isValid = isCollectionValid((Collection<?>)value);
    }
    else if(value instanceof DateFilter<?>){
      isValid = isDateFilterValid((DateFilter<?>)value);
    }
    else if(value instanceof String){
      isValid = isStringValid((String)value);
    }
    else {
      isValid = value != null;
    }
    return isValid;
  }


  private static boolean isCollectionValid(final Collection<?> collection){
    return !collection.isEmpty() && !collection.stream()
        .map(ValidatorUtil::isValid)
        .collect(Collectors.toSet())
        .contains(false);
  }

  private static boolean isDateFilterValid(final DateFilter<?> date){
    return isValid(date.getStart()) || isValid(date.getUntil());
  }

  private static boolean isStringValid(final String string){
    return string != null && !string.trim().isEmpty();
  }

  public static String removeControlCharacters(final String string){
    if(string == null){
      return null;
    }
    return string.replaceAll("[!*%]", "");
  }
}
