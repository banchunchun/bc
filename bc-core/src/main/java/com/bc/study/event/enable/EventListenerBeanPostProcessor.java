package com.bc.study.event.enable;

import com.bc.study.event.EventListener;
import com.bc.study.event.EventPublishHelper;
import com.bc.study.event.EventPublisher;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author bc
 * @Date 2020-07-07 10:08
 * @title 请详细描述该类含义
 */
public class EventListenerBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }

  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    if (bean instanceof EventListener) {
      EventListener<?> listener = (EventListener<?>) bean;
      EventPublishHelper.get().addEventListener(listener);
      Map<String, EventPublisher> publishers = this.applicationContext.getBeansOfType(EventPublisher.class);
      if (publishers != null) {
        for (EventPublisher publisher : publishers.values()) {
          publisher.addEventListener(listener);
        }
      }
    }
    return bean;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
