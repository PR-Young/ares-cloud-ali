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

package com.ares.flowable.common.constant;


public class ProcessConstants {

    /**
     * 动态数据
     */
    public static final String DATA_TYPE = "dynamic";

    /**
     * 单个审批人
     */
    public static final String USER_TYPE_ASSIGNEE = "assignee";


    /**
     * 候选人
     */
    public static final String USER_TYPE_USERS = "candidateUsers";


    /**
     * 审批组
     */
    public static final String USER_TYPE_GROUPS = "candidateGroups";

    /**
     * 单个审批人
     */
    public static final String PROCESS_APPROVAL = "approval";

    /**
     * 会签人员
     */
    public static final String PROCESS_MULTI_INSTANCE_USER = "userList";

    /**
     * namespace
     */
    public static final String NAMESPACE = "http://flowable.org/bpmn";

    /**
     * 会签节点
     */
    public static final String PROCESS_MULTI_INSTANCE = "multiInstance";

    /**
     * 自定义属性 dataType
     */
    public static final String PROCESS_CUSTOM_DATA_TYPE = "dataType";

    /**
     * 自定义属性 userType
     */
    public static final String PROCESS_CUSTOM_USER_TYPE = "userType";

    /**
     * 初始化人员
     */
    public static final String PROCESS_INITIATOR = "INITIATOR";


    /**
     * 流程跳过
     */
    public static final String FLOWABLE_SKIP_EXPRESSION_ENABLED = "_FLOWABLE_SKIP_EXPRESSION_ENABLED";


}
