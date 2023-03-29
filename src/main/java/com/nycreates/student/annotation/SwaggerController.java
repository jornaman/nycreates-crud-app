package com.nycreates.student.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Controller;

@Controller
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SwaggerController {}
