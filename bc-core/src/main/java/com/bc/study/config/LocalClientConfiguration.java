package com.bc.study.config;

/**
 * @author bc
 * @Date 2020-06-17 11:20
 * @title 请详细描述该类含义
 */
public class LocalClientConfiguration {

  private static LocalClientConfiguration INSTANCE = new LocalClientConfiguration();
  private LocalClientProperties localProperies;

  public LocalClientConfiguration() {
    localProperies  = new LocalClientProperties();
    localProperies.load();
  }

  public static LocalClientProperties getLocalProperties() {
    return INSTANCE.localProperies;
  }
}
