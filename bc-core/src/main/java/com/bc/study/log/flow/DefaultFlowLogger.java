package com.bc.study.log.flow;

import com.bc.study.log.BizLogger;
import com.bc.study.log.FlowLogger;
import com.bc.study.log.TComLogger;
import org.slf4j.Logger;

/**
 * @author bc
 * @Date 2020-06-15 15:14
 * @title 请详细描述该类含义
 */
public class DefaultFlowLogger implements FlowLogger {

  private Logger logger;

  private BizLogger bizLogger;

  private TComLogger tComLogger;

  public DefaultFlowLogger(Logger logger) {
    this.logger = logger;
    this.bizLogger = new DefaultBizLogger(logger);
    this.tComLogger = new DefaultTComLogger(logger);
  }

  @Override
  public Logger defaultLogger() {
    return logger;
  }

  @Override
  public BizLogger bizLogger() {
    return this.bizLogger;
  }

  @Override
  public TComLogger tcomLogger() {
    return this.tComLogger;
  }
}
