package com.hnv99.mall.service;

import com.hnv99.mall.dto.OmsOrderReturnApplyResult;
import com.hnv99.mall.dto.OmsReturnApplyQueryParam;
import com.hnv99.mall.dto.OmsUpdateStatusParam;
import com.hnv99.mall.model.OmsOrderReturnApply;

import java.util.List;

public interface OmsOrderReturnApplyService {
    /**
     * Paginate query applications
     */
    List<OmsOrderReturnApply> list(OmsReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum);

    /**
     * Bulk delete applications
     */
    int delete(List<Long> ids);

    /**
     * Modify specific application status
     */
    int updateStatus(Long id, OmsUpdateStatusParam statusParam);

    /**
     * Get specific application details
     */
    OmsOrderReturnApplyResult getItem(Long id);
}

