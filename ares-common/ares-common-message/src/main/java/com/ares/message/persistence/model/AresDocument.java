package com.ares.message.persistence.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @description:
 * @author: Young
 * @date: 2020/11/11
 * @see: com.ares.message.persistence.model ESDemo.java
 **/
@Data
@Document(indexName = "ares-document")
public class AresDocument {

    @Id
    private String id;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Keyword)
    private String key;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String body;


    public AresDocument() {
    }

    public AresDocument(String id, String name, String key, String content, String body) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.content = content;
        this.body = body;
    }
}
