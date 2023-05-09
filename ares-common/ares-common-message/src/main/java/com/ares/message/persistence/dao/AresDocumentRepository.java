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

package com.ares.message.persistence.dao;

import com.ares.message.persistence.model.AresDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Young
 * @date: 2020/11/11
 * @see: com.ares.message.persistence.dao ESDemoRepository.java
 **/
@Repository
public interface AresDocumentRepository extends ElasticsearchRepository<AresDocument, String> {
    /**
     * 在内容中模糊查询
     *
     * @param content
     * @param pageable
     * @return
     */
    Page<AresDocument> findByContentIsContaining(String content, Pageable pageable);

    /**
     * 在body中模糊查询
     *
     * @param body
     * @param pageable
     * @return
     */
    Page<AresDocument> findByBodyIsContaining(String body, Pageable pageable);

    /**
     * 根据名称查询
     *
     * @param name
     * @param key
     * @param pageable
     * @return
     */
    Page<AresDocument> findByNameLikeOrKeyIsLike(String name, String key, Pageable pageable);

    /**
     * 根据内容或body查询
     *
     * @param name
     * @param key
     * @param pageable
     * @return
     */
    Page<AresDocument> findByContentIsContainingOrBodyIsContaining(String name, String key, Pageable pageable);
}
