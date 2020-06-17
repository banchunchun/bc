package com.bc.study.jdbc;

import com.bc.study.boot.InitializeException;
import com.bc.study.log.TComLogs;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeReference;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableConfigurationProperties({DataSourceProperties.class, IbatisProperties.class})
public class IbatisAutoConfiguration {

  @Autowired
  IbatisProperties mybatisProperties;

  private List<Class<?>> ibatisDefaultHandler;

  public IbatisAutoConfiguration() {
    // 基础数据类型handler暂时不支持业务上自定义
    ibatisDefaultHandler = new ArrayList<Class<?>>();
    ibatisDefaultHandler.add(Boolean.class);
    ibatisDefaultHandler.add(boolean.class);
    ibatisDefaultHandler.add(Byte.class);
    ibatisDefaultHandler.add(byte.class);
    ibatisDefaultHandler.add(Short.class);
    ibatisDefaultHandler.add(short.class);
    ibatisDefaultHandler.add(Integer.class);
    ibatisDefaultHandler.add(int.class);
    ibatisDefaultHandler.add(Long.class);
    ibatisDefaultHandler.add(long.class);
    ibatisDefaultHandler.add(Float.class);
    ibatisDefaultHandler.add(float.class);
    ibatisDefaultHandler.add(Double.class);
    ibatisDefaultHandler.add(double.class);
    ibatisDefaultHandler.add(Reader.class);
    ibatisDefaultHandler.add(String.class);
    ibatisDefaultHandler.add(BigInteger.class);
    ibatisDefaultHandler.add(BigDecimal.class);
    ibatisDefaultHandler.add(InputStream.class);
    ibatisDefaultHandler.add(Byte[].class);
    ibatisDefaultHandler.add(byte[].class);
    ibatisDefaultHandler.add(Object.class);
    ibatisDefaultHandler.add(Date.class);
    ibatisDefaultHandler.add(java.sql.Date.class);
    ibatisDefaultHandler.add(java.sql.Time.class);
    ibatisDefaultHandler.add(java.sql.Timestamp.class);
  }

  @Bean
  @ConditionalOnMissingBean
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext context) throws IOException {
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    Configuration c = new Configuration();
    c.setLogPrefix("bc.dao.");
    ResourcePatternResolver rr = new PathMatchingResourcePatternResolver();
    if (mybatisProperties.getConfigLocation() != null && mybatisProperties.getConfigLocation().trim().length() > 0) {
      Configuration configuration = loadConfiguration(rr.getResources(mybatisProperties.getConfigLocation()));
      bean.setConfiguration(configuration);
    }
    if (mybatisProperties.getMapperLocations() != null && mybatisProperties.getMapperLocations().trim().length() > 0) {
      Resource[] rs = rr.getResources(mybatisProperties.getMapperLocations());
      bean.setMapperLocations(rs);
    }
    if (mybatisProperties.getTypeAliasesPackage() != null && mybatisProperties.getTypeAliasesPackage().trim().length() > 0) {
      bean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
    }
    SqlSessionFactory sqlSessionFactory = null;
    try {
      sqlSessionFactory = bean.getObject();
      Map<String, Object> intercepts = context.getBeansWithAnnotation(Intercepts.class);
      if (intercepts != null) {
        for (Object interceptor : intercepts.values()) {
          sqlSessionFactory.getConfiguration().addInterceptor((Interceptor) interceptor);
        }
      }
    } catch (Exception e) {
      throw new InitializeException("create SqlSessionFactory fail", e);
    }
    return sqlSessionFactory;
  }

  @SuppressWarnings("unchecked")
  private <T> Configuration loadConfiguration(Resource[] rs) throws IOException {
    Configuration configuration = null;
    if (rs != null) {
      for (Resource r : rs) {
        TComLogs.info("ibatis xml conf : {}", r);
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(r.getInputStream(), null, null);
        xmlConfigBuilder.parse();
        if (configuration == null) {
          configuration = xmlConfigBuilder.getConfiguration();
        } else {
          Configuration temp = xmlConfigBuilder.getConfiguration();
          if (temp.getTypeAliasRegistry().getTypeAliases() != null) {
            for (Entry<String, Class<?>> typeAliases : temp.getTypeAliasRegistry().getTypeAliases().entrySet()) {
              configuration.getTypeAliasRegistry().registerAlias(typeAliases.getKey(), typeAliases.getValue());
            }
          }
          if (temp.getTypeHandlerRegistry().getTypeHandlers() != null) {
            for (TypeHandler typeHandler : temp.getTypeHandlerRegistry().getTypeHandlers()) {
              boolean isDefaultHandler = false;
              if (typeHandler instanceof TypeReference) {
                try {
                  TypeReference<T> typeReference = (TypeReference<T>) typeHandler;
                  if (ibatisDefaultHandler.contains(typeReference.getRawType())) {
                    isDefaultHandler = true;
                  }
                } catch (Throwable t) {
                  // maybe users define the TypeReference with a different type and are not assignable, so just ignore it
                }
              }
              if (!isDefaultHandler) {
                configuration.getTypeHandlerRegistry().register(typeHandler);
              }
            }
          }
        }
      }
    }
    return configuration;
  }

  @Bean
  @ConditionalOnMissingBean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    ExecutorType executorType = this.mybatisProperties.getExecutorType();
    if (executorType != null) {
      return new SqlSessionTemplate(sqlSessionFactory, executorType);
    } else {
      return new SqlSessionTemplate(sqlSessionFactory);
    }
  }

  @Bean
  @ConditionalOnMissingBean
  public DataSourceTransactionManager transactionManager(DataSource dataSource) {
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    transactionManager.setDataSource(dataSource);
    return transactionManager;
  }
}
