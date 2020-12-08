package com.huajun123.biz;

import com.huajun123.domain.*;
import com.huajun123.entity.Blog;

public interface ISearchBiz {
  Item buildItemForSearchFromProject(Project project);
  SearchResult searchResultsForWechat(SearchRequest request);
  BlogItem constructBlogItemFromBlog(Blog blog);
}
