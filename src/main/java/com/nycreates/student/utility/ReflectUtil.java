package com.nycreates.student.utility;

import com.nycreates.student.model.dto.ReflectClassDto;
import com.nycreates.student.model.dto.ReflectFieldDto;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class ReflectUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtil.class);

  public static ReflectClassDto describe(final Object instance){

    final Class<?> instanceClass = instance.getClass();
    BeanInfo beanInfo;
    try {
      beanInfo = Introspector.getBeanInfo(instanceClass);

    } catch (IntrospectionException e) {
      LOGGER.error("ERROR", e);
      return null;
    }
    final ReflectClassDto reflection = new ReflectClassDto(instanceClass);
    for(PropertyDescriptor desc : beanInfo.getPropertyDescriptors()){
      if("class".equals(desc.getName())){
        continue;
      }
      final ReflectFieldDto<?> field = new ReflectFieldDto<>(desc.getPropertyType());
      field.setField(desc.getName());
      field.setValue(invokeReadMethod(desc, instance));
      reflection.getFields().add(field);
    }
    return reflection;
  }

  private Object invokeReadMethod(final PropertyDescriptor descriptor, final Object instance){

    try {
      return descriptor.getReadMethod().invoke(instance);

    } catch (IllegalAccessException | InvocationTargetException e) {
      LOGGER.error("ERROR", e);
      return null;
    }
  }
}
