package com.bc.study.log.flow;

/**
 * @author bc
 * @Date 2020-06-15 14:49
 * @title 请详细描述该类含义
 */
public class BizLoggerFormat {

  public static String SEPARATOR = "|";

  public static String SEPARATOR_PROPERTY = "=";

  /**
   * @param logType 业务类型
   * @param properties 数组必须是2的倍数，根据规范key和value必须成对出现
   */
  public static String format(String logType, Object... properties) {
    StringBuilder sb = new StringBuilder();
    sb.append(logType);
    if (properties != null && properties.length > 0) {
      sb.append(SEPARATOR);
      int length = properties.length;
      for (int i = 1; i < length + 1; i++) {
        sb.append(properties[i - 1]);
        if (i < length) {
          if (i % 2 == 0) {
            sb.append(SEPARATOR);
          } else {
            sb.append(SEPARATOR_PROPERTY);
          }
        }
      }
    }
    return sb.toString();
  }

  public static String format2(String logType, Object... properties) {
    StringBuilder sb = new StringBuilder();
    sb.append(logType);
    if (properties != null && properties.length > 0) {
      sb.append(SEPARATOR);
      int length = properties.length;
      for (int i = 1; i < length + 1; i++) {
        sb.append(properties[i - 1]);
        if (i < length) {
          sb.append(SEPARATOR);
        }
      }
    }
    return sb.toString();
  }

}
