package com.huajun123.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogQuery extends SearchRequest {
  private int page;
  private int limit;
  private Map<String, List<String>> map;

  @Override
  public String toString() {
    return super.toString()+"BlogQuery{" +
            "page=" + page +
            ", limit=" + limit +
            ", map=" + map +
            '}';
  }
}
