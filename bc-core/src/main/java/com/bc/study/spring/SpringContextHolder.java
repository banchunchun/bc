package com.bc.study.spring;

import org.springframework.context.ApplicationContext;

/**
 * @author bc
 * @Date 2020-06-17 11:27
 * @title 请详细描述该类含义
 */
public class SpringContextHolder {

  private static ApplicationContext context;

  public static void set(ApplicationContext c) {
    context = c;
  }

  public static ApplicationContext get() {
    return context;
  }
}
