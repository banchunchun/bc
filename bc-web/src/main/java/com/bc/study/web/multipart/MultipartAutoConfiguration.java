package com.bc.study.web.multipart;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

/**
 * {@code MultipartAutoConfiguration} customize tomcat's <tt>maxSwallowSize</tt> for multipart
 * upload.
 *
 * @author bc
 */
@Configuration
@ConditionalOnClass({Servlet.class, Tomcat.class, StandardServletMultipartResolver.class,
    MultipartConfigElement.class})
@ConditionalOnProperty(prefix = "spring.http.multipart", name = "enabled", matchIfMissing = true)
@AutoConfigureBefore(EmbeddedServletContainerAutoConfiguration.class)
@AutoConfigureAfter(org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration.class)
public class MultipartAutoConfiguration {

  @Bean
  public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
    TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
    tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
      if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
        ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
      }

    });
    return tomcat;
  }


}
