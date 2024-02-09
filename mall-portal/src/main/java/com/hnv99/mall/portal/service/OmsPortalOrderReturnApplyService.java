package com.hnv99.mall.portal.service;

import com.hnv99.mall.portal.domain.OmsOrderReturnApplyParam;

public interface OmsPortalOrderReturnApplyService {
    /**
     * Submit application
     */
    int create(OmsOrderReturnApplyParam returnApply);
}
