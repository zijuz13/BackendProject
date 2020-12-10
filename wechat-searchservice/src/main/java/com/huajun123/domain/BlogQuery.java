package com.huajun123.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogQuery extends SearchRequest {
  private int page;
  private int limit;
}
