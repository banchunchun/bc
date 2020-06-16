package com.bc.study.web.rest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration Auto-configuration} enables
 * {@link RestResponses}.
 *
 * @author bc
 */
@Configuration
public class RestResponseAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public RestResponses restResponses(MessageSource messageSource) {
    return new RestResponses(messageSource);
  }

}
