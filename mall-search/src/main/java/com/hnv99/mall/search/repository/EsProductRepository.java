package com.hnv99.mall.search.repository;

import com.hnv99.mall.search.domain.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Long> {
    /**
     * Search query.
     *
     * @param name      Product name
     * @param subTitle  Product subtitle
     * @param keywords  Product keywords
     * @param page      Pagination information
     */
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable page);

}
