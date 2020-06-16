package com.bc.study.web.rest;

import com.bc.study.status.CommonStatus;
import com.bc.study.status.Status;
import java.util.Collections;
import org.springframework.context.MessageSource;

/**
 * @author bc
 * @Date 2020-06-16 13:58
 * @title 请详细描述该类含义
 */
public class RestResponses {

  private MessageSource messageSource;

  public RestResponses(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  /**
   * Returns the {@link RestResponse} with {@link CommonStatus#ok}.
   */
  public RestResponse ok() {
    return ok(null);
  }

  public RestResponse ok(Object result) {
    return build(CommonStatus.ok, result);
  }

  public <T> RestResultResponse<T> ok2(T result) {

    return RestResponseBuilder.builder(CommonStatus.ok)
        .result(result)
        .messageSource(messageSource)
        .build3(new Object[0]);
  }

  public RestResponse okEmptyMapIfResultNull(Object result) {
    return build(CommonStatus.ok, result == null ? Collections.emptyMap() : result);
  }

  /**
   * Creates {@code RestResponse} with specified status and result.
   *
   * @param status the status
   * @param result the result
   * @return {@code RestResponse} according to status
   */
  public RestResponse build(Status status, Object result) {
    return build(status, result, new Object[0]);
  }

  /**
   * Creates {@code RestResponse} with specified status and message arguments and can contains a result.
   *
   * @param status the status
   * @param result the result
   * @param args the args used format message
   * @return {@code RestResponse} according to status
   */
  public RestResponse build(Status status, Object result, Object[] args) {
    return RestResponseBuilder.builder(status)
        .result(result)
        .messageSource(messageSource)
        .build(args);
  }

  /**
   * Creates {@code RestResponse} with specified status and message arguments and can contains a result.
   *
   * @param status the status
   * @param result the result
   * @param args the args used format message
   * @return {@code RestResponse} according to status
   */
  public <T> RestResultResponse<T> build3(Status status, T result, Object[] args) {
    return RestResponseBuilder.builder(status)
        .result(result)
        .messageSource(messageSource)
        .build3(args);
  }

  /**
   * Creates {@code RestResponse } with specified status and message arguments.
   *
   * @param status the status
   * @param args the args used format message
   * @return {@code RestResponse} according to status
   */
  public RestResponse build2(Status status, Object... args) {
    return build(status, null, args);
  }

  public RestResponse error() {
    return error(CommonStatus.error);
  }

  public RestResponse error(Status status) {
    return build(status, null);
  }

}
