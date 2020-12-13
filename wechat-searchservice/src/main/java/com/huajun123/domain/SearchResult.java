package com.huajun123.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {
    private List<Item> items;
    private Map<String,List<String>> specs;
    private int totalPages;
    private int total;
}
