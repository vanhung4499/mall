package com.hnv99.mall.service.impl;

import com.hnv99.mall.mapper.CmsPreferenceAreaMapper;
import com.hnv99.mall.model.CmsPreferenceArea;
import com.hnv99.mall.model.CmsPreferenceAreaExample;
import com.hnv99.mall.service.CmsPreferenceAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsPreferenceAreaServiceImpl implements CmsPreferenceAreaService {
    @Autowired
    private CmsPreferenceAreaMapper preferenceAreaMapper;

    @Override
    public List<CmsPreferenceArea> listAll() {
        return preferenceAreaMapper.selectByExample(new CmsPreferenceAreaExample());
    }
}

