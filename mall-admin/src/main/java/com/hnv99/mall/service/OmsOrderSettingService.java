package com.hnv99.mall.service;

import com.hnv99.mall.model.OmsOrderSetting;

public interface OmsOrderSettingService {
    /**
     * Get specified order setting
     */
    OmsOrderSetting getItem(Long id);

    /**
     * Update specified order setting
     */
    int update(Long id, OmsOrderSetting orderSetting);
}
