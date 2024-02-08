package com.hnv99.mall.service;

import com.hnv99.mall.dto.PmsBrandParam;
import com.hnv99.mall.model.PmsBrand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PmsBrandService {
    /**
     * Get all brands
     */
    List<PmsBrand> listAllBrand();

    /**
     * Create brand
     */
    int createBrand(PmsBrandParam pmsBrandParam);

    /**
     * Update brand
     */
    @Transactional
    int updateBrand(Long id, PmsBrandParam pmsBrandParam);

    /**
     * Delete brand
     */
    int deleteBrand(Long id);

    /**
     * Bulk delete brands
     */
    int deleteBrand(List<Long> ids);

    /**
     * Paginated query of brands
     */
    List<PmsBrand> listBrand(String keyword, Integer showStatus, int pageNum, int pageSize);

    /**
     * Get brand details
     */
    PmsBrand getBrand(Long id);

    /**
     * Update display status
     */
    int updateShowStatus(List<Long> ids, Integer showStatus);

    /**
     * Update manufacturer status
     */
    int updateFactoryStatus(List<Long> ids, Integer factoryStatus);
}
