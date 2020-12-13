package com.huajun123.domain;

import java.util.Map;

public class SearchRequest {
  private String name;
  private Map<String,String> filter;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "name='" + name + '\'' +
                ", filter=" + filter +
                '}';
    }
}
