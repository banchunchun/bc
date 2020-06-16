package com.bc.study.log;

import com.bc.study.log.flow.DefaultFlowLogger;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bc
 * @Date 2020-06-15 15:17
 * @title 请详细描述该类含义
 */
public class FlowLoggers {

  private static Map<String, FlowLogger> loggerCache = new HashMap<>();

  private final static FlowLogger root = new DefaultFlowLogger(LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME));

  public static FlowLogger getLogger(Class<?> clazz) {

    return new DefaultFlowLogger(LoggerFactory.getLogger(clazz));
  }

  public static FlowLogger getLogger(String className) {
    FlowLogger childLogger = loggerCache.get(className);
    if (null != childLogger) {
      return childLogger;
    }

    synchronized (root) {
      FlowLogger logger = loggerCache.get(className);
      if (logger == null) {
        logger = new DefaultFlowLogger(LoggerFactory.getLogger(className));
        loggerCache.put(className, logger);
      }
      return logger;
    }
  }
}
