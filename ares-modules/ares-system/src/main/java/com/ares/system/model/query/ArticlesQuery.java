/*
 *
 *  * !******************************************************************************
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

package com.ares.system.model.query;

import com.ares.core.model.query.BaseQuery;
import lombok.Data;

/**
 * @description:
 * @author: Young
 * @date: 2023/8/2
 * @see: com.ares.system.model.query.ArticlesQuery.java
 **/
@Data
public class ArticlesQuery extends BaseQuery {
   
    private String title;
   
    private String name;
   
    private String type;
   
    private String status;
}