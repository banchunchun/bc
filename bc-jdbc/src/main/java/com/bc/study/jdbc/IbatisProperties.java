/**
 *
 */
package com.bc.study.jdbc;

import org.apache.ibatis.session.ExecutorType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 */
@ConfigurationProperties("bc.mybatis")
public class IbatisProperties {

  private String configLocation = "classpath*:mybatis.xml";

  private String mapperLocations = "classpath*:mapper/**/*.xml";

  private String typeAliasesPackage;

  private ExecutorType executorType;

  public String getConfigLocation() {
    return configLocation;
  }

  public void setConfigLocation(String configLocation) {
    this.configLocation = configLocation;
  }

  public String getMapperLocations() {
    return mapperLocations;
  }

  public void setMapperLocations(String mapperLocations) {
    this.mapperLocations = mapperLocations;
  }

  public String getTypeAliasesPackage() {
    return typeAliasesPackage;
  }

  public void setTypeAliasesPackage(String typeAliasesPackage) {
    this.typeAliasesPackage = typeAliasesPackage;
  }

  public ExecutorType getExecutorType() {
    return executorType;
  }

  public void setExecutorType(ExecutorType executorType) {
    this.executorType = executorType;
  }

}
