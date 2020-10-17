package com.huajun123.domain;

import java.util.List;
import java.util.Map;

public class SearchResult {
    private List<Item> items;
    private Map<String,List<String>> specs;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Map<String, List<String>> getSpecs() {
        return specs;
    }

    public void setSpecs(Map<String, List<String>> specs) {
        this.specs = specs;
    }

    public SearchResult(List<Item> items, Map<String, List<String>> specs) {
        this.items = items;
        this.specs = specs;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "items=" + items +
                ", specs=" + specs +
                '}';
    }
}
