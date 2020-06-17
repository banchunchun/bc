package com.bc.study.jdbc.dataSource.enable;

import com.bc.study.boot.ApplicationInitializer;
import com.bc.study.boot.InitializeException;
import com.bc.study.jdbc.dataSource.CommonDataSouceAutoConf;
import com.bc.study.jdbc.dataSource.ShardingDataSouceAutoConf;
import com.bc.study.log.TComLogs;
import com.bc.study.utils.StringUtil;
import org.springframework.context.ConfigurableApplicationContext;

/**
 */
public class JdbcInitializer implements ApplicationInitializer {

  @Override
  public void init(ConfigurableApplicationContext applicationContext) throws InitializeException {
    String jdbcType = applicationContext.getEnvironment().getProperty("bc.jdbc.type");
    TComLogs.info("init jdbc config, jdbc type is {} ", jdbcType);
    if (StringUtil.equalsIgnoreCase(jdbcType, "sharding")) {
      registerBean(ShardingDataSouceAutoConf.class, applicationContext);
    } else {
      registerBean(CommonDataSouceAutoConf.class, applicationContext);
    }

  }

}
