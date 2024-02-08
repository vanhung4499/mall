package com.hnv99.mall.service.impl;

import com.hnv99.mall.mapper.OmsCompanyAddressMapper;
import com.hnv99.mall.model.OmsCompanyAddress;
import com.hnv99.mall.model.OmsCompanyAddressExample;
import com.hnv99.mall.service.OmsCompanyAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OmsCompanyAddressServiceImpl implements OmsCompanyAddressService {
    @Autowired
    private OmsCompanyAddressMapper companyAddressMapper;
    @Override
    public List<OmsCompanyAddress> list() {
        return companyAddressMapper.selectByExample(new OmsCompanyAddressExample());
    }
}
