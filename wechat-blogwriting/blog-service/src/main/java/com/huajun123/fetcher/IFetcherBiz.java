package com.huajun123.fetcher;

import com.huajun123.entity.Blog;

import java.util.List;

public interface IFetcherBiz {
    List<Blog> fetchBlogsFromNewsWebsite();
}
