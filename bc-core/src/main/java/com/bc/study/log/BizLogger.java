package com.bc.study.log;

/**
 * @author bc
 * @Date 2020-06-15 14:42
 * @title 请详细描述该类含义
 */
public interface BizLogger {

  void debug(String bizType, Object... properties);

  void info(String bizType, Object... properties);

  void warn(String bizType, Object... properties);

  void warn(String bizType, Throwable e, Object... properties);

  void error(String bizType, Throwable e, Object... properties);

  void error(String bizType, Object... properties);
}
