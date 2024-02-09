package com.hnv99.mall.portal.dao;

import com.hnv99.mall.model.CmsSubject;
import com.hnv99.mall.model.PmsBrand;
import com.hnv99.mall.model.PmsProduct;
import com.hnv99.mall.portal.domain.FlashPromotionProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HomeDao {

    /**
     * Get recommended brands
     */
    List<PmsBrand> getRecommendBrandList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * Get flash sale products
     */
    List<FlashPromotionProduct> getFlashProductList(@Param("flashPromotionId") Long flashPromotionId, @Param("sessionId") Long sessionId);

    /**
     * Get new product recommendations
     */
    List<PmsProduct> getNewProductList(@Param("offset") Integer offset,@Param("limit") Integer limit);

    /**
     * Get popular recommendations
     */
    List<PmsProduct> getHotProductList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * Get recommended topics
     */
    List<CmsSubject> getRecommendSubjectList(@Param("offset") Integer offset, @Param("limit") Integer limit);
}
