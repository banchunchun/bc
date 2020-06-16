package com.bc.study.boot;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;

/**
 * @author bc
 * @Date 2020-06-16 17:08
 * @title 请详细描述该类含义
 */
public class ClassBeanReference extends RuntimeBeanNameReference {

  private ConfigurableListableBeanFactory factory;

  private Class<?> referneceClass;

  public ClassBeanReference(ConfigurableListableBeanFactory factory, Class<?> referneceClass) {
    super("empty");
    this.factory = factory;
    this.referneceClass = referneceClass;
  }

  @Override
  public String getBeanName() {
    String[] beanNames = factory.getBeanNamesForType(this.referneceClass);
    if (beanNames == null || beanNames.length == 0) {
      throw new BeanDefinitionValidationException("not found instance " + this.referneceClass);
    }
    if (beanNames.length > 1) {
      throw new BeanDefinitionValidationException("multiple instances " + this.referneceClass);
    }
    return beanNames[0];
  }

  @Override
  public Object getSource() {
    return factory.getBean(this.referneceClass);
  }
}
