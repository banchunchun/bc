package com.bc.study.web;

import com.bc.study.boot.AbstractDefaultPropertiesSetter;
import java.util.HashMap;
import java.util.Map;

/**
 * add web mvc default properites setter
 *
 * @author bc
 * @Date 2020-06-16 9:47
 */
public class WebMvcDefaultPropertiesSetter extends AbstractDefaultPropertiesSetter {

  @Override
  protected Map<String, Object> getDefaultMap() {
    Map<String, Object> props = new HashMap<>();
    props.put("spring.messages.basename", "messages/message,messages/default");
    props.put("spring.jackson.date-format", "yyyy-MM-dd HH:mm:ss");
    props.put("spring.jackson.time-zone", "Asia/Shanghai");
    return props;
  }
}
