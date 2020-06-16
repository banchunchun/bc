package com.bc.study.log;

import org.slf4j.Logger;

/**
 * @author bc
 * @Date 2020-06-15 15:07
 * @title 请详细描述该类含义
 */
public interface FlowLogger {

  Logger defaultLogger();

  BizLogger bizLogger();

  TComLogger tcomLogger();
}
