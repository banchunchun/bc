package com.bc.study.log.flow;

import com.bc.study.log.TComLogger;
import com.bc.study.log.TimeWatcher;
import org.slf4j.Logger;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author bc
 * @Date 2020-06-15 15:00
 * @title 请详细描述该类含义
 */
public class DefaultTComLogger implements TComLogger {

  private Logger logger;

  public DefaultTComLogger(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void debug(String formatMsg, Object... properties) {
    if (logger.isDebugEnabled()) {
      logger.debug(format(formatMsg, properties));
    }
  }

  @Override
  public boolean isDebug() {
    return this.logger.isDebugEnabled();
  }

  @Override
  public boolean isInfo() {
    return this.logger.isInfoEnabled();
  }

  @Override
  public void info(String formatMsg, Object... properties) {
    if (logger.isInfoEnabled()) {
      logger.info(format(formatMsg, properties));
    }
  }

  @Override
  public void warn(String formatMsg, Object... properties) {
    if (logger.isWarnEnabled()) {
      logger.warn(format(formatMsg, properties));
    }
  }

  @Override
  public void warn(String formatMsg, Throwable e, Object... properties) {
    if (logger.isWarnEnabled()) {
      logger.warn(format(formatMsg, properties), e);
    }
  }

  @Override
  public void error(String formatMsg, Throwable e, Object... properties) {
    if (logger.isErrorEnabled()) {
      logger.error(format(formatMsg, properties), e);
    }
  }

  @Override
  public void error(String formatMsg, Object... properties) {
    if (logger.isErrorEnabled()) {
      logger.error(format(formatMsg, properties));
    }
  }

  @Override
  public void debug(TimeWatcher timeWatch, String formatMsg, Object... properties) {
    if (logger.isDebugEnabled()) {
      logger.debug(format(formatMsg, properties) + " " + timeWatch.outputTimeList());
    }
  }

  @Override
  public void info(TimeWatcher timeWatch, String formatMsg, Object... properties) {
    if (logger.isInfoEnabled()) {
      logger.info(format(formatMsg, properties) + " " + timeWatch.outputTimeList());
    }
  }


  private String format(String formatMsg, Object... properties) {
    return MessageFormatter.arrayFormat(formatMsg, properties).getMessage();
  }
}
