package com.bc.study.log;

/**
 * @author bc
 * @Date 2020-06-15 14:59
 * @title 请详细描述该类含义
 */
public interface TComLogger {

  void debug(String formatMsg, Object... properties);

  boolean isDebug();

  boolean isInfo();

  void info(String formatMsg, Object... properties);

  void warn(String formatMsg, Object... properties);

  void warn(String formatMsg, Throwable e, Object... properties);

  void error(String formatMsg, Throwable e, Object... properties);

  void error(String formatMsg, Object... properties);

  void debug(TimeWatcher timeWatch, String formatMsg, Object... properties);

  void info(TimeWatcher timeWatch, String formatMsg, Object... properties);
}
