package com.huajun123.biz;

import com.huajun123.domain.Item;
import com.huajun123.domain.Project;
import com.huajun123.domain.SearchRequest;
import com.huajun123.domain.SearchResult;

public interface ISearchBiz {
  Item buildItemForSearchFromProject(Project project);
  SearchResult searchResultsForWechat(SearchRequest request);
}
