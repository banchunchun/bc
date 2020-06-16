package com.bc.study.log.flow;

import com.bc.study.log.TBizLogger;
import com.bc.study.log.TimeWatcher;
import org.slf4j.Logger;

/**
 * @author bc
 * @Date 2020-06-15 14:45
 * @title 请详细描述该类含义
 */
public class DefaultBizLogger implements TBizLogger {

  private Logger logger;

  public DefaultBizLogger(Logger logger) {
    this.logger = logger;
  }

  @Override
  public void debug(TimeWatcher timeWatcher, String bizType, Object... properties) {
    if (logger.isDebugEnabled()) {
      logger.debug(BizLoggerFormat.format(bizType, properties) + "|" + timeWatcher.outputTimeList());
    }
  }

  @Override
  public void info(TimeWatcher timeWatcher, String bizType, Object... properties) {
    if (logger.isInfoEnabled()) {
      logger.info(BizLoggerFormat.format(bizType, properties) + "|" + timeWatcher.outputTimeList());
    }
  }

  @Override
  public void debug(String bizType, Object... properties) {
    if (logger.isDebugEnabled()) {
      logger.debug(BizLoggerFormat.format(bizType, properties));
    }
  }

  @Override
  public void info(String bizType, Object... properties) {
    if (logger.isInfoEnabled()) {
      logger.debug(BizLoggerFormat.format(bizType, properties));
    }
  }

  @Override
  public void warn(String bizType, Object... properties) {
    if (logger.isWarnEnabled()) {
      logger.debug(BizLoggerFormat.format(bizType, properties));
    }
  }

  @Override
  public void warn(String bizType, Throwable e, Object... properties) {
    if (logger.isWarnEnabled()) {
      logger.debug(BizLoggerFormat.format(bizType, properties), e);
    }
  }

  @Override
  public void error(String bizType, Throwable e, Object... properties) {
    if (logger.isErrorEnabled()) {
      logger.error(BizLoggerFormat.format(bizType, properties), e);
    }
  }

  @Override
  public void error(String bizType, Object... properties) {
    if (logger.isErrorEnabled()) {
      logger.error(BizLoggerFormat.format(bizType, properties));
    }
  }
}
