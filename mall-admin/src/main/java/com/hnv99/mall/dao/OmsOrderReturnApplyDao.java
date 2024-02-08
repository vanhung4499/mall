package com.hnv99.mall.dao;

import com.hnv99.mall.dto.OmsOrderReturnApplyResult;
import com.hnv99.mall.dto.OmsReturnApplyQueryParam;
import com.hnv99.mall.model.OmsOrderReturnApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OmsOrderReturnApplyDao {
    /**
     * Query application list
     */
    List<OmsOrderReturnApply> getList(@Param("queryParam") OmsReturnApplyQueryParam queryParam);

    /**
     * Get application details
     */
    OmsOrderReturnApplyResult getDetail(@Param("id")Long id);
}
