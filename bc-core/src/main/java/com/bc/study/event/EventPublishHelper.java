package com.bc.study.event;

/**
 * @author bc
 * @Date 2020-07-07 10:10
 * @title 请详细描述该类含义
 */
public class EventPublishHelper extends EventPublisher {

  private EventPublishHelper() {
  }

  private static class SingletonContainer {

    private static EventPublishHelper INSTANCE = new EventPublishHelper();
  }

  public static EventPublishHelper get() {
    return SingletonContainer.INSTANCE;
  }
}
