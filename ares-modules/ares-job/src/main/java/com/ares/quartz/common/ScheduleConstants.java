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

package com.ares.quartz.common;

/**
 * 任务调度通用常量
 */
public interface ScheduleConstants {
    public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME_";

    /**
     * 执行目标key
     */
    public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

    public static final String TRIGGER = "_TRIGGER";

    public static final String TRIGGER_GROUP = "_TRIGGER_GROUP";

    /**
     * 默认
     */
    public static final Integer MISFIRE_DEFAULT = 0;

    /**
     * 立即触发执行
     */
    public static final Integer MISFIRE_IGNORE_MISFIRES = 1;

    /**
     * 触发一次执行
     */
    public static final Integer MISFIRE_FIRE_AND_PROCEED = 2;

    /**
     * 不触发立即执行
     */
    public static final Integer MISFIRE_DO_NOTHING = 3;

    /**
     * 失败状态
     */
    public static final Integer FAIL_STATUS = 1;

    /**
     * 成功状态
     */
    public static final Integer SUCCESS_STATUS = 0;


    /**
     * 任务的关闭的状态
     */
    public static final Integer STOP_STATUS = 2;

    public enum Status {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private Integer value;

        private Status(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}
