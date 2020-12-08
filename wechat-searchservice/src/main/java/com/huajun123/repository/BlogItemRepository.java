package com.huajun123.repository;

import com.huajun123.domain.BlogItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogItemRepository extends ElasticsearchRepository<BlogItem,Long> {
}
