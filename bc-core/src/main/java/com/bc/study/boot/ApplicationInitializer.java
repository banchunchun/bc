package com.bc.study.boot;

import com.bc.study.utils.StringUtil;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

/**
 * boot 初始化执行接口，在应用启动生命周期中首先被执行。 {@link #order()}方法用于标识该执行器的优先级，数字越小优先级越高， 默认取值 ApplicationInitializer.COMMON_ORDER : {@value #COMMON_ORDER}
 *
 * @author bc
 * @Date 2020-06-16 16:59
 * @title 请详细描述该类含义
 */
public interface ApplicationInitializer {

  static int COMMON_ORDER = 100;

  static int HIGH_ORDER = 1;

  static int LOW_ORDER = 200;

  static BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

  void init(ConfigurableApplicationContext appContext);

  default int order() {
    return COMMON_ORDER;
  }

  default String registerBean(Class<?> clazz, ConfigurableApplicationContext appContext) {
    return registerBean(clazz, appContext, new AnnotationBeanNameGenerator());
  }

  default public String registerBean(Class<?> clazz, ConfigurableApplicationContext appContext, Class<?>... autoInjectVals) {
    return registerBean(clazz, appContext, new AnnotationBeanNameGenerator(), autoInjectVals);
  }

  default public String registerBean(Class<?> clazz, ConfigurableApplicationContext appContext, BeanNameGenerator generator) {
    return registerBean(clazz, appContext, new AnnotationBeanNameGenerator(), new Class<?>[]{});
  }

  default String registerBean(Class<?> clazz, ConfigurableApplicationContext appContext, BeanNameGenerator generator, Class<?>... autoInjectVals) {
    if (!(appContext instanceof BeanDefinitionRegistry)) {
      throw new IllegalArgumentException(appContext + " not instanceof BeanDefinitionRegistry");
    }
    try {
      BeanDefinitionRegistry registry = (BeanDefinitionRegistry) appContext;
      SimpleMetadataReaderFactory factory = new SimpleMetadataReaderFactory(clazz.getClassLoader());
      MetadataReader metadataReader = factory.getMetadataReader(clazz.getName());
      ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
      String beanName = generator.generateBeanName(sbd, registry);
      if (null != autoInjectVals) {
        for (Class<?> injectClazz : autoInjectVals) {
          String attrName = null;
          for (Field field : injectClazz.getDeclaredFields()) {
            if (field.getType().equals(injectClazz)) {
              attrName = field.getName();
              break;
            }
          }
          if (StringUtils.isEmpty(attrName)) {
            throw new InitializeException(injectClazz + " no matching attributes were found");
          }
          ClassBeanReference reference = new ClassBeanReference(appContext.getBeanFactory(), injectClazz);
          sbd.getPropertyValues().add(attrName, reference);
        }
      }
      //注册bean
      registry.registerBeanDefinition(beanName, sbd);
      return beanName;
    } catch (Exception ex) {
      throw new InitializeException("", ex);
    }
  }

  /**
   * 将某个包下的索引class都注入到bean 容器中
   */
  default public void registerBean(String packageName, ConfigurableApplicationContext appContext) throws InitializeException {
    String basePackage = ClassUtils.convertClassNameToResourcePath(appContext.getEnvironment().resolveRequiredPlaceholders(packageName));
    String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + basePackage + "/*.class";
    try {
      Resource[] resources = appContext.getResources(packageSearchPath);
      MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
      for (Resource resource : resources) {
        MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
        String className = metadataReader.getClassMetadata().getClassName();
        Class<?> beanClass = Class.forName(className);
        registerBean(beanClass, appContext);
      }
    } catch (IOException | ClassNotFoundException e) {
      throw new InitializeException("register package bean fail", e);
    }
  }

  default public void registerBean(Package scanPackage, ConfigurableApplicationContext appContext) throws InitializeException {
    registerBean(scanPackage.getName(), appContext);
  }



  /**
   * 将包含目标注解的类注入到bean容器里，扫描的包是根据bee-core的scanPackage配置进行注入的
   */
  default public void registerBeanByAnnotationType(Class<? extends Annotation> annotationType, ConfigurableApplicationContext appContext) {
    BeanDefinitionRegistry registry = (BeanDefinitionRegistry) appContext;
    ResourceLoader resourceLoader = (ResourceLoader) appContext;
    ClassPathBeanDefinitionScanner interceptsScanner = new ClassPathBeanDefinitionScanner(registry);
    interceptsScanner.setResourceLoader(resourceLoader);
    interceptsScanner.addIncludeFilter(new AnnotationTypeFilter(annotationType));
    interceptsScanner.scan(getScanPackages(appContext));
  }

  /**
   * 将包含类注入到bean容器里，扫描的包是根据bee-core的scanPackage配置进行注入的
   */

  default public void registerBean(TypeFilter includeFilter, ConfigurableApplicationContext appContext) {
    BeanDefinitionRegistry registry = (BeanDefinitionRegistry) appContext;
    ResourceLoader resourceLoader = (ResourceLoader) appContext;
    ClassPathBeanDefinitionScanner interceptsScanner = new ClassPathBeanDefinitionScanner(registry);
    interceptsScanner.setResourceLoader(resourceLoader);
    interceptsScanner.addIncludeFilter(includeFilter);
    interceptsScanner.scan(getScanPackages(appContext));
  }

  default public String[] getScanPackages(ConfigurableApplicationContext applicationContext) {
    String[] scanPackages;
    String scanPackage = System.getProperty("bc.config.scanPackage");
    List<String> scanpackageList = new ArrayList<String>();
    List<String> configSplitList = new ArrayList<String>();
    if (!StringUtils.isBlank(scanPackage)) {
      for (String pack : scanPackage.split(",")) {
        configSplitList.add(pack);
      }
    }
    String springContextScanPackage = applicationContext.getEnvironment().getProperty("bc.config.scanPackage");
    if (!StringUtils.isBlank(springContextScanPackage)) {
      for (String pack : springContextScanPackage.split(",")) {
        configSplitList.add(pack);
      }
    }
    for (String pack : configSplitList) {
      if (!scanpackageList.contains(pack)) {
        boolean ignore = false;
        for (String existPack : scanpackageList) {
          if (pack.startsWith(existPack)) {
            ignore = true;
          } else if (existPack.startsWith(pack)) {
            scanpackageList.remove(existPack);
            break;
          }
        }
        if (!ignore) {
          scanpackageList.add(pack);
        }
      }
    }
    scanPackages = new String[scanpackageList.size()];
    scanpackageList.toArray(scanPackages);
    return scanPackages;
  }

  default public <T> void findAnnotationOnClass(Class<?> clazz, Class<T> target, List<T> annotations) {
    if (clazz == null || clazz.equals(Object.class) || StringUtil.startsWith(clazz.getName(), "java")) {
      return;
    }
    for (Annotation annotation : clazz.getAnnotations()) {
      Class<?> annClass = annotation.annotationType();
      if (annClass.equals(target)) {
        annotations.add((T) annotation);
      }
      if (!annClass.equals(clazz)) {
        findAnnotationOnClass(annClass, target, annotations);
      }
    }
    findAnnotationOnClass(clazz.getSuperclass(), target, annotations);
    for (Class<?> intf : clazz.getInterfaces()) {
      findAnnotationOnClass(intf, target, annotations);
    }
  }
}
