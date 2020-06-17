package com.bc.study.jdbc.ibatis.enable;

import com.bc.study.boot.EnableInitializer;
import com.bc.study.jdbc.dataSource.enable.JdbcEnable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bc
 * @Date 2020-06-17 10:07
 * @title 请详细描述该类含义
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableInitializer(IbatisInitializer.class)
@JdbcEnable
public @interface IbaitsEnable {

  /**
   * 是否自动注入Mapper注解的dao
   *
   * @return
   */
  boolean autoMapper() default true;

  boolean autoIntercepts() default true;
}
