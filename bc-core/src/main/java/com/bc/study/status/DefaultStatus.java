package com.bc.study.status;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author bc
 * @Date 2020-06-15 15:51
 * @title 请详细描述该类含义
 */
public class DefaultStatus implements Status, Serializable {

  private final int code;
  private final String message;

  public DefaultStatus(int code, String message) {
    this.code = code;
    this.message = message;
  }


  public static DefaultStatus toStatus(int code, String message) {
    return new DefaultStatus(code, message);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        .append("code", code)
        .append("message", message)
        .toString();
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
