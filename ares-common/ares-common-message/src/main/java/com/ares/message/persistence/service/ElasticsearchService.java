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

import com.ares.message.persistence.model.AresDocument;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
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

    private ElasticsearchRestTemplate restTemplate;

    @Autowired
    public ElasticsearchService(ElasticsearchRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void save(AresDocument document) {
        restTemplate.save(document);
    }

    public void saveAll(List<AresDocument> documents) {
        restTemplate.save(documents);
    }

    public void delete(AresDocument document) {
        restTemplate.delete(document);
    }

    public void deleteById(String id) {
        restTemplate.delete(id, AresDocument.class);
    }

    public List<AresDocument> query(String value) {
        List<AresDocument> aresDocuments = new ArrayList<>();
        MatchQueryBuilder key = new MatchQueryBuilder("key", value);
        MatchQueryBuilder name = new MatchQueryBuilder("name", value);
        MatchQueryBuilder body = new MatchQueryBuilder("body", value);
        MatchQueryBuilder content = new MatchQueryBuilder("content", value);

        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        queryBuilder.should(key).should(name).should(body).should(content);
        NativeSearchQuery query = new NativeSearchQuery(queryBuilder);

        SearchHits<AresDocument> iterable = restTemplate.search(query, AresDocument.class);
        iterable.getSearchHits().forEach(document -> {
            aresDocuments.add(document.getContent());
        });

        return aresDocuments;
    }

    public Iterable<AresDocument> queryByFiled(String fieldName, Object value, Pageable pageable) {
        List<AresDocument> aresDocuments = new ArrayList<>();
        MatchQueryBuilder queryBuilder = new MatchQueryBuilder(fieldName, value);
        NativeSearchQuery query = new NativeSearchQuery(queryBuilder);
        query.setPageable(pageable);
        SearchHits<AresDocument> documents = restTemplate.search(query, AresDocument.class);
        documents.getSearchHits().forEach(document -> {
            aresDocuments.add(document.getContent());
        });
        return aresDocuments;
    }

    public Iterable<AresDocument> queryAll(Pageable pageable) {
        List<AresDocument> aresDocuments = new ArrayList<>();
        MatchAllQueryBuilder queryBuilder = new MatchAllQueryBuilder();
        NativeSearchQuery query = new NativeSearchQuery(queryBuilder);
        query.setPageable(pageable);
        SearchHits<AresDocument> documents = restTemplate.search(query, AresDocument.class);
        documents.getSearchHits().forEach(document -> {
            aresDocuments.add(document.getContent());
        });
        return aresDocuments;
    }

}
