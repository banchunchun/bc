package com.bc.study.web.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author bc
 * @Date 2020-06-16 10:21
 * @title 请详细描述该类含义
 */
@ConfigurationProperties(prefix = "rest")
public class RestConfigProperty {

  private int maxConnection = 100;

  private int maxTimeoutSeconds = 10;

  public int getMaxConnection() {
    return maxConnection;
  }

  public void setMaxConnection(int maxConnection) {
    this.maxConnection = maxConnection;
  }

  public int getMaxTimeoutSeconds() {
    return maxTimeoutSeconds;
  }

  public void setMaxTimeoutSeconds(int maxTimeoutSeconds) {
    this.maxTimeoutSeconds = maxTimeoutSeconds;
  }
}
