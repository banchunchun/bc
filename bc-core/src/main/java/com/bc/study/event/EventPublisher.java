package com.bc.study.event;

import com.bc.study.utils.ClassUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author bc
 * @Date 2020-07-06 19:18
 * @title 请详细描述该类含义
 */
public abstract class EventPublisher {

  private static Logger logger = LoggerFactory.getLogger(EventPublisher.class);

  private Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();

  public synchronized <L> void notice(L event) {
    Class clazz = event.getClass();
    do {
      List<EventListener<?>> eventListeners = this.listeners.get(clazz);
      notice(clazz, event, eventListeners);
      clazz = clazz.getSuperclass();
    } while (!Object.class.equals(clazz));
    List<EventListener<?>> nullClazzEventListeners = this.listeners.get(NULL_LISTENER.class);
    notice(clazz, event, nullClazzEventListeners);
  }

  public synchronized <L> void notice(Class<L> clazz, L event) {
    List<EventListener<?>> eventListeners = this.listeners.get(clazz);
    notice(clazz, event, eventListeners);
    List<EventListener<?>> nullClazzEventListeners = this.listeners.get(NULL_LISTENER.class);
    notice(clazz, event, nullClazzEventListeners);
  }

  @SuppressWarnings("unchecked")
  protected synchronized <L> void notice(Class<L> clazz, L event, List<EventListener<?>> eventListeners) {
    if (eventListeners != null) {
      for (EventListener<?> temp : eventListeners) {
        EventListener<L> eventListener = (EventListener<L>) temp;
        try {
          eventListener.onEvent(event);
        } catch (Throwable e) {
          logger.error("notice event error ", e);
        }
      }
    }
  }

  public synchronized void addEventListener(EventListener<?> listener) {
    List<Class<?>> eventClasses = ClassUtil.getGenericClass(listener.getClass());
    if (eventClasses == null || eventClasses.size() == 0) {
      addEventListener(NULL_LISTENER.class, listener);
    } else {
      for (Class<?> clazz : eventClasses) {
        addEventListener(clazz, listener);
      }
    }
  }

  protected synchronized void addEventListener(Class<?> clazz, EventListener<?> listener) {
    List<EventListener<?>> eventListeners = this.listeners.get(clazz);
    if (eventListeners == null) {
      eventListeners = new ArrayList<EventListener<?>>();
      this.listeners.put(clazz, eventListeners);
    }
    if (!eventListeners.contains(listener)) {
      eventListeners.add(listener);
    }
  }

  static class NULL_LISTENER {

  }
}
