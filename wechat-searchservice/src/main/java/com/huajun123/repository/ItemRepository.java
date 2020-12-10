package com.huajun123.repository;

import com.huajun123.domain.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

public interface ItemRepository extends ElasticsearchRepository<Item,Long> {
}
