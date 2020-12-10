package com.huajun123.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogResults {
    private List<BlogItem> items;
    private int total;
    private int totalPages;
    private Map<String,Object> aggs;

    public BlogResults(List<BlogItem> content, int parseInt, int totalPages) {
        this.items=content;
        this.total=parseInt;
        this.totalPages=totalPages;
    }
}
