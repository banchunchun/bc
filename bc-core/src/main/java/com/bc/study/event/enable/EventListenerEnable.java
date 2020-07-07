package com.bc.study.event.enable;

import com.bc.study.boot.EnableInitializer;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bc
 * @Date 2020-07-07 10:07
 * @title 请详细描述该类含义
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableInitializer(value = EventListenerInitializer.class)
public @interface EventListenerEnable {

}
