package com.ares.system.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description:
 * @author: Young
 * @date: 2020/08/07
 * @see: com.ares.system.jobs DemoJob.java
 **/
public class DemoJob {

    private static Logger logger = LoggerFactory.getLogger(DemoJob.class);

    public void demo1() {
        logger.info("demo1 job start!");
    }

    public void demo2(String a, String b) {
        logger.info("demo2 job start,paramA={},paramB={}", a, b);
    }
}
