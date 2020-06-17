package com.bc.study.jdbc.dataSource;

import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 */
@EnableConfigurationProperties({DataSourceProperties.class, SlaveDataSourceProperties.class, MasterDataSourceProperties.class})
public class CommonDataSouceAutoConf {

  @Bean
  public DynamicDataSourceProvider dynamicDataSourceProvider(MasterDataSourceProperties properties, SlaveDataSourceProperties twoproperties) {
    return new TwoJdbcDataSourceProvider(properties, twoproperties);
  }
}
