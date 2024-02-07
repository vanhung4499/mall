package com.hnv99.mall.mapper;

import com.hnv99.mall.model.CmsPreferenceAreaProductRelation;
import com.hnv99.mall.model.CmsPreferenceAreaProductRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CmsPreferenceAreaProductRelationMapper {
    long countByExample(CmsPreferenceAreaProductRelationExample example);

    int deleteByExample(CmsPreferenceAreaProductRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CmsPreferenceAreaProductRelation row);

    int insertSelective(CmsPreferenceAreaProductRelation row);

    List<CmsPreferenceAreaProductRelation> selectByExample(CmsPreferenceAreaProductRelationExample example);

    CmsPreferenceAreaProductRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") CmsPreferenceAreaProductRelation row, @Param("example") CmsPreferenceAreaProductRelationExample example);

    int updateByExample(@Param("row") CmsPreferenceAreaProductRelation row, @Param("example") CmsPreferenceAreaProductRelationExample example);

    int updateByPrimaryKeySelective(CmsPreferenceAreaProductRelation row);

    int updateByPrimaryKey(CmsPreferenceAreaProductRelation row);
}