package com.bc.study.event;

/**
 * @author bc
 * @Date 2020-07-06 19:17
 * @title 请详细描述该类含义
 */
public interface EventListener<T> {

  void onEvent(T data);
}
