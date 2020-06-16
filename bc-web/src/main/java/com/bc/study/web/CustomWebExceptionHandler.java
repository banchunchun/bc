package com.bc.study.web;

import com.bc.study.status.CommonStatus;
import com.bc.study.web.rest.RestResponse;
import com.bc.study.web.rest.RestResponses;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.SizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.PathMatcher;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsProcessor;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UrlPathHelper;

/**
 * Global exception handler for custom exception translate.
 *
 * @author bc
 */
@ControllerAdvice
public class CustomWebExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  private RestResponses restResponses;

  private RequestMappingHandlerMapping handlerMapping;

  private MultipartProperties multipartProperties;

  @Autowired
  public void setHandlerMapping(RequestMappingHandlerMapping handlerMapping) {
    this.handlerMapping = handlerMapping;
  }

  @Autowired(required = false)
  public void setMultipartProperties(MultipartProperties multipartProperties) {
    this.multipartProperties = multipartProperties;
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status,
      WebRequest request) {
    return new ResponseEntity<>(
        restResponses.error(CommonStatus.httpMethodNotSupport),
        HttpStatus.METHOD_NOT_ALLOWED
    );
  }

  /**
   * Converts {@link MultipartException} to {@code RestResponse}.
   *
   * @return the converted {@code RestResponse}.
   */
  @ExceptionHandler(MultipartException.class)
  @ResponseBody
  public RestResponse handleException(MultipartException ex, HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    logger.error("", ex);
    processCorsRequest(request, response);
    boolean isExceedLimit =
        (ex.getCause() instanceof IllegalStateException && ex.getCause()
            .getCause() instanceof SizeException)
            || ex instanceof MaxUploadSizeExceededException; // for commons-upload compatibility
    if (isExceedLimit) {
      if (multipartProperties != null && multipartProperties.getEnabled()) {
        return restResponses.build2(
            CommonStatus.maxUploadSizeExceeded2,
            multipartProperties.getMaxFileSize(),
            multipartProperties.getMaxRequestSize()
        );
      } else {
        return restResponses.error(CommonStatus.maxUploadSizeExceeded);
      }
    }
    return restResponses.error();
  }


  private void processCorsRequest(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    if (CorsUtils.isCorsRequest(request)) {
      Map<String, CorsConfiguration> corsConfigs = handlerMapping.getCorsConfigurations();
      if (corsConfigs != null && !corsConfigs.isEmpty()) {
        PathMatcher pathMatcher = handlerMapping.getPathMatcher();
        UrlPathHelper urlPathHelper = handlerMapping.getUrlPathHelper();
        CorsConfiguration corsConfiguration = getCorsConfiguration(
            urlPathHelper, request, corsConfigs, pathMatcher
        );
        if (corsConfiguration != null) {
          CorsProcessor corsProcessor = handlerMapping.getCorsProcessor();
          corsProcessor.processRequest(corsConfiguration, request, response);
        }
      }
    }
  }

  private CorsConfiguration getCorsConfiguration(
      UrlPathHelper urlPathHelper,
      HttpServletRequest request,
      Map<String, CorsConfiguration> corsConfigs,
      PathMatcher pathMatcher
  ) {
    String lookupPath = urlPathHelper.getLookupPathForRequest(request);
    for (Map.Entry<String, CorsConfiguration> entry : corsConfigs.entrySet()) {
      if (pathMatcher.match(entry.getKey(), lookupPath)) {
        return entry.getValue();
      }
    }
    return null;
  }

}
