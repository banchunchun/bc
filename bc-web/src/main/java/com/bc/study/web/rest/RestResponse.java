package com.bc.study.web.rest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author bc
 * @Date 2020-06-16 10:23
 * @title 请详细描述该类含义
 */
public class RestResponse {

  private int code;

  private String message;

  public RestResponse() {

  }

  public RestResponse(int code) {
    this.code = code;
  }

  public RestResponse(int code, String message) {
    this(code);
    this.message = message;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }


  public boolean isSuccess() {
    return this.code == 0;
  }
}
