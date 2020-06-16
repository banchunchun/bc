package com.bc.study.boot;

/**
 * @author bc
 * @Date 2020-06-16 17:07
 * @title 请详细描述该类含义
 */
public class InitializeException extends RuntimeException {

  private static final long serialVersionUID = 1318021105305110145L;

  public InitializeException(String msg) {
    super(msg);
  }

  public InitializeException(String msg, Throwable e) {
    super(msg, e);
  }
}
