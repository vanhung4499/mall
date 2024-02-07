package com.hnv99.mall.mapper;

import com.hnv99.mall.model.PmsProductCategoryAttributeRelation;
import com.hnv99.mall.model.PmsProductCategoryAttributeRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsProductCategoryAttributeRelationMapper {
    long countByExample(PmsProductCategoryAttributeRelationExample example);

    int deleteByExample(PmsProductCategoryAttributeRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsProductCategoryAttributeRelation row);

    int insertSelective(PmsProductCategoryAttributeRelation row);

    List<PmsProductCategoryAttributeRelation> selectByExample(PmsProductCategoryAttributeRelationExample example);

    PmsProductCategoryAttributeRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") PmsProductCategoryAttributeRelation row, @Param("example") PmsProductCategoryAttributeRelationExample example);

    int updateByExample(@Param("row") PmsProductCategoryAttributeRelation row, @Param("example") PmsProductCategoryAttributeRelationExample example);

    int updateByPrimaryKeySelective(PmsProductCategoryAttributeRelation row);

    int updateByPrimaryKey(PmsProductCategoryAttributeRelation row);
}