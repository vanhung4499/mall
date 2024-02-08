package com.hnv99.mall.dao;

import com.hnv99.mall.model.CmsPreferenceAreaProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CmsPreferenceAreaProductRelationDao {
    /**
     * Batch create
     */
    int insertList(@Param("list") List<CmsPreferenceAreaProductRelation> prefrenceAreaProductRelationList);
}
