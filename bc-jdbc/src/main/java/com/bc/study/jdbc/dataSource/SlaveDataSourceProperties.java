package com.bc.study.jdbc.dataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("bee.datasource.slave")
public class SlaveDataSourceProperties extends DataSourceProperties {
}
