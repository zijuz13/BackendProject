package com.huajun123.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.Map;

@Document(indexName = "wechat", type = "item", shards = 3, replicas = 1)
public class Item {
    @Id
    private Long id;
    @Field(type=FieldType.Text,index = true)
    private String name;
    @Field(type=FieldType.Keyword,index = false)
    private String video;
    @Field(type = FieldType.Keyword, index = false)
    private String description;
    @Field(type=FieldType.Keyword,index=false)
    private String salepoint;
    @Field(type=FieldType.Keyword,index = false)
    private String image;
    private Map<String, List<Object>> map;

    public Map<String, List<Object>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<Object>> map) {
        this.map = map;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", video='" + video + '\'' +
                ", description='" + description + '\'' +
                ", salepoint='" + salepoint + '\'' +
                ", image='" + image + '\'' +
                ", map=" + map +
                '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalepoint() {
        return salepoint;
    }

    public void setSalepoint(String salepoint) {
        this.salepoint = salepoint;
    }

}
