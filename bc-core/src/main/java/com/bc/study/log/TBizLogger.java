package com.bc.study.log;

/**
 * log format : [className] bizType|timecost |propertys[0]=propertys[1]|[propertys2]=propertys[3]....
 * @author bc
 * @Date 2020-06-15 14:48
 * @title 请详细描述该类含义
 */
public interface TBizLogger extends BizLogger {

  void debug(TimeWatcher timeWatcher, String bizType, Object... properties);

  void info(TimeWatcher timeWatcher, String bizType, Object... properties);
}
