/*
 *
 *  *  ******************************************************************************
 *  *  * Copyright (c) 2021 - 9999, ARES
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *  *****************************************************************************
 *
 */

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
