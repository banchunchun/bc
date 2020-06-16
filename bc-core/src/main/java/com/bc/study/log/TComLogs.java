package com.bc.study.log;

/**
 * 通用日志器
 *
 * @author bc
 * @Date 2020-06-15 15:04
 * @title 通用日志器
 */
public class TComLogs {

  private static TComLogger getLocationAwareLogger(final int depth) {
    String className = sun.reflect.Reflection.getCallerClass(depth).getName();
    return FlowLoggers.getLogger(className).tcomLogger();
  }

  private static TComLogger getLogger() {
    return getLocationAwareLogger(4);
  }

  public static void debug(String formatMsg, Object... properties) {
    getLogger().debug(formatMsg, properties);
  }

  public static boolean isDebug() {
    return getLogger().isDebug();
  }

  public static void info(String formatMsg, Object... properties) {
    getLogger().info(formatMsg, properties);
  }

  public static boolean isInfo() {
    return getLogger().isInfo();
  }

  public static void warn(String formatMsg, Object... properties) {
    getLogger().warn(formatMsg, properties);
  }

  public static void warn(String formatMsg, Throwable e, Object... properties) {
    getLogger().warn(formatMsg, e, properties);
  }

  public static void error(String formatMsg, Throwable e, Object... properties) {
    getLogger().error(formatMsg, e, properties);
  }

  public static void error(String formatMsg, Object... properties) {
    getLogger().error(formatMsg, properties);
  }

  public static void debug(TimeWatcher timeWatch, String formatMsg, Object... properties) {
    getLogger().debug(timeWatch, formatMsg, properties);
  }

  public static void info(TimeWatcher timeWatch, String formatMsg, Object... properties) {
    getLogger().info(timeWatch, formatMsg, properties);
  }

}
