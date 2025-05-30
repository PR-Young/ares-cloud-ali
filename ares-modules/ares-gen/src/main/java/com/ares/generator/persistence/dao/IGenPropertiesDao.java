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

package com.ares.generator.persistence.dao;


import com.ares.core.dao.IBaseDao;
import com.ares.generator.persistence.model.GenProperties;
import com.ares.generator.persistence.model.query.GenPropertiesQuery;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: ares
 * @date: 2025-04-21 10:19:03
 **/
@Repository
public interface IGenPropertiesDao extends IBaseDao<GenProperties, GenPropertiesQuery> {
    GenProperties getByUser(Long userId);
}
