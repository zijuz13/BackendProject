package com.huajun123.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName="web",type="blog",shards = 3,replicas = 1)
public class BlogItem {
    @Id
    private Long id;
    @Field(type=FieldType.Text,index = true)
    private String all;
    @Field(type= FieldType.Keyword,index = false)
    private String author;
    @Field(type=FieldType.Keyword,index=false)
    private String summary;
    @Field(type=FieldType.Keyword,index = false)
    private String image_uri;
    @Field(type=FieldType.Text,index = false)
    private String title;
    @Field(type=FieldType.Keyword,index = false)
    private String displaytime;
    private Boolean commentstatus;
    private Map<String,Object> specs;
}
