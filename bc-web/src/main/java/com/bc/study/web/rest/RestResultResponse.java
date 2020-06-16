package com.bc.study.web.rest;

/**
 * @author bc
 * @Date 2020-06-16 13:56
 * @title 请详细描述该类含义
 */
public class RestResultResponse<T> extends RestResponse {

  private T result;

  public RestResultResponse() {

  }

  public RestResultResponse(int code, String message) {
    super(code, message);
  }

  public T getResult() {
    return result;
  }

  public void setResult(T result) {
    this.result = result;
  }
}
