package com.hnv99.mall.service;

import com.hnv99.mall.model.OmsOrderReturnReason;

import java.util.List;

public interface OmsOrderReturnReasonService {
    /**
     * Add a return reason
     */
    int create(OmsOrderReturnReason returnReason);

    /**
     * Modify a return reason
     */
    int update(Long id, OmsOrderReturnReason returnReason);

    /**
     * Batch delete return reasons
     */
    int delete(List<Long> ids);

    /**
     * Get return reasons by page
     */
    List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum);

    /**
     * Batch modify return reason status
     */
    int updateStatus(List<Long> ids, Integer status);

    /**
     * Get details of a single return reason
     */
    OmsOrderReturnReason getItem(Long id);
}
