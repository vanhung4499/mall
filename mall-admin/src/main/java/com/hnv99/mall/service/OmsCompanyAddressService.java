package com.hnv99.mall.service;

import com.hnv99.mall.model.OmsCompanyAddress;

import java.util.List;

public interface OmsCompanyAddressService {
    /**
     * Get all delivery addresses
     */
    List<OmsCompanyAddress> list();
}
