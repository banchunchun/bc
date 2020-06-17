package com.bc.study.jdbc.dataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("bc.datasource.master")
public class MasterDataSourceProperties extends DataSourceProperties {
}
