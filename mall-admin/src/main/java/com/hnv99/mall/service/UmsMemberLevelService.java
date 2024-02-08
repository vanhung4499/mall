package com.hnv99.mall.service;

import com.hnv99.mall.model.UmsMemberLevel;

import java.util.List;

public interface UmsMemberLevelService {
    /**
     * Get all member levels
     * @param defaultStatus Whether it is a default member
     */
    List<UmsMemberLevel> list(Integer defaultStatus);
}
