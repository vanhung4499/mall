package com.hnv99.mall.dao;

import com.hnv99.mall.model.CmsSubjectProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CmsSubjectProductRelationDao {
    /**
     * Batch create
     */
    int insertList(@Param("list") List<CmsSubjectProductRelation> subjectProductRelationList);
}
