package com.bc.study.event.enable;

import com.bc.study.boot.InitializeException;
import com.bc.study.boot.ModuleInitializer;
import com.bc.study.event.EventPublishHelper;
import java.lang.annotation.Annotation;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author bc
 * @Date 2020-07-07 10:07
 * @title 请详细描述该类含义
 */
public class EventListenerInitializer implements ModuleInitializer {

  @Override
  public void init(ConfigurableApplicationContext appContext) {
    registerBean(EventListenerBeanPostProcessor.class, appContext);
    appContext.getBeanFactory().registerSingleton("eventPublisher", EventPublishHelper.get());
  }

  @Override
  public void init(Annotation enableAnno, ConfigurableApplicationContext appContext) throws InitializeException {

  }
}
