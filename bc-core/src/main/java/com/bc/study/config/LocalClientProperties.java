package com.bc.study.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author bc
 * @Date 2020-06-17 11:15
 * @title 请详细描述该类含义
 */
public class LocalClientProperties extends Properties {

  private Class<?> applicationClasss;

  public Class<?> getApplicationClasss() {
    return applicationClasss;
  }

  public void setApplicationClasss(Class<?> applicationClasss) {
    this.applicationClasss = applicationClasss;
  }

  public void load() {
    loadDefaultFromClass();
    loadDefaultProperties();
  }

  public void load(InputStream inputStream) {
    try {
      super.load(inputStream);
    } catch (IOException e) {
      // logger.warn("load property fail.");
    }
  }

  @SuppressWarnings({"restriction", "deprecation"})
  protected void loadDefaultFromClass() {
    Class<?> parentClass = null;
    SpringBootApplication startClass = null;
    int i = 1;
    while ((parentClass = sun.reflect.Reflection.getCallerClass(i)) != null) {
      if (parentClass != null) {
        startClass = parentClass.getAnnotation(SpringBootApplication.class);
        if (startClass != null) {
          break;
        }
      }
      i++;
    }
    if (startClass != null) {
      this.applicationClasss = parentClass;
    }
  }


  protected void loadDefaultProperties() {
    InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("bc.properties");
    if (inputStream != null) {
      load(inputStream);
    }
  }
}
