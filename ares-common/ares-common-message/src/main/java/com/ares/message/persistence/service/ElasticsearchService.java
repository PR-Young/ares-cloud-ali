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

package com.ares.message.persistence.service;

import com.ares.message.persistence.dao.AresDocumentRepository;
import com.ares.message.persistence.model.AresDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.CriteriaQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Young
 * @date: 2022/1/5
 * @see: com.ares.message.persistence.service.ElasticsearchService.java
 */
@Service
public class ElasticsearchService {

    private ElasticsearchTemplate elasticsearchTemplate;
    private AresDocumentRepository repository;

    @Autowired
    public ElasticsearchService(ElasticsearchTemplate elasticsearchTemplate,AresDocumentRepository repository) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.repository = repository;
    }

    public void save(AresDocument document) {
        repository.save(document);
    }

    public void saveAll(List<AresDocument> documents) {
        repository.saveAll(documents);
    }

    public void delete(AresDocument document) {
        repository.delete(document);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public List<AresDocument> query(String value) {
        List<AresDocument> aresDocuments = new ArrayList<>();
        Criteria criteria = new Criteria("key")
                .or("name")
                .or("body")
                .or("content").matches(value);
        CriteriaQueryBuilder builder =  new CriteriaQueryBuilder(criteria);
        Query query = new CriteriaQuery(builder);
        SearchHits<AresDocument> iterable = elasticsearchTemplate.search(query, AresDocument.class);
        iterable.getSearchHits().forEach(document -> {
            aresDocuments.add(document.getContent());
        });

        return aresDocuments;
    }

    public Iterable<AresDocument> queryByFiled(String fieldName, Object value, Pageable pageable) {
        List<AresDocument> aresDocuments = new ArrayList<>();
        Criteria criteria = new Criteria(fieldName).matches(value);
        CriteriaQueryBuilder builder =  new CriteriaQueryBuilder(criteria);
        Query query = new CriteriaQuery(builder);
        query.setPageable(pageable);
        SearchHits<AresDocument> documents = elasticsearchTemplate.search(query, AresDocument.class);
        documents.getSearchHits().forEach(document -> {
            aresDocuments.add(document.getContent());
        });
        return aresDocuments;
    }

    public Iterable<AresDocument> queryAll(Pageable pageable) {
        List<AresDocument> aresDocuments = new ArrayList<>();
        Query query = Query.findAll();
        query.setPageable(pageable);
        SearchHits<AresDocument> documents = elasticsearchTemplate.search(query, AresDocument.class);
        documents.getSearchHits().forEach(document -> {
            aresDocuments.add(document.getContent());
        });
        return aresDocuments;
    }

}
