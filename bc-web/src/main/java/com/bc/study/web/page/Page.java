package com.bc.study.web.page;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bc
 * @Date 2020-06-16 9:57
 * @title 请详细描述该类含义
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Page {

  /**
   * Defines all of fields that can be order by.
   *
   * @return fields can be order by
   */
  String[] allowOrderByFields();

  /**
   * Defines the default order by field.
   *
   * @return the default order by field
   */
  String orderBy() default "";
}
