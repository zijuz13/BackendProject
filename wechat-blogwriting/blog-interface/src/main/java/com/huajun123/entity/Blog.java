package com.huajun123.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    private int id;
    private String author;
    private String content;
    @JsonProperty("content_short")
    private String summary; //
    private String status;
    private String image_uri;
    private String title;
    @JsonProperty("display_time")
    private String displaytime; //
    private Boolean commentstatus;
    private int importance;
}
