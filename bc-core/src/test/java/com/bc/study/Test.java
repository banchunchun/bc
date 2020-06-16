package com.bc.study;

import com.bc.study.log.TimeWatcher;

/**
 * @author bc
 * @Date 2020-06-15 14:27
 * @title 请详细描述该类含义
 */
public class Test {

  public static void main(String[] args) throws InterruptedException {

    TimeWatcher timeWatcher = TimeWatcher.createTimeWatch("测试程序");

    Thread.sleep(1000);

    timeWatcher.addStep("sleep");
    System.out.println("output");
    timeWatcher.addStep("output");

    String ss = timeWatcher.outputTimeList();

    System.out.println(ss);

  }
}
