package com.hnv99.mall.service;

import com.hnv99.mall.dto.PmsProductParam;
import com.hnv99.mall.dto.PmsProductQueryParam;
import com.hnv99.mall.dto.PmsProductResult;
import com.hnv99.mall.model.PmsProduct;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PmsProductService {
    /**
     * Create product
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    int create(PmsProductParam productParam);

    /**
     * Get update information by product ID
     */
    PmsProductResult getUpdateInfo(Long id);

    /**
     * Update product
     */
    @Transactional
    int update(Long id, PmsProductParam productParam);

    /**
     * Paginate products
     */
    List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum);

    /**
     * Batch modify verification status
     * @param ids product ids
     * @param verifyStatus verification status
     * @param detail verification details
     */
    @Transactional
    int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail);

    /**
     * Batch modify product publish status
     */
    int updatePublishStatus(List<Long> ids, Integer publishStatus);

    /**
     * Batch modify product recommendation status
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * Batch modify new product status
     */
    int updateNewStatus(List<Long> ids, Integer newStatus);

    /**
     * Batch delete products
     */
    int updateDeleteStatus(List<Long> ids, Integer deleteStatus);

    /**
     * Fuzzy search by product name or product number
     */
    List<PmsProduct> list(String keyword);
}
