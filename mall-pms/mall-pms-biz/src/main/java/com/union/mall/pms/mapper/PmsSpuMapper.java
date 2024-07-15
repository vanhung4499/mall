package com.union.mall.pms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.pms.model.entity.PmsSpu;
import com.union.mall.pms.model.query.SpuPageQuery;
import com.union.mall.pms.model.vo.PmsSpuPageVO;
import com.union.mall.pms.model.vo.SpuPageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PmsSpuMapper extends BaseMapper<PmsSpu> {

    /**
     * Admin - Product Pagination List
     *
     * @param page        pagination parameters
     * @param queryParams query parameters
     * @return paginated list of products
     */
    List<PmsSpuPageVO> listPagedSpu(Page<PmsSpuPageVO> page, SpuPageQuery queryParams);

    /**
     * APP - Product Pagination List
     *
     * @param page        pagination parameters
     * @param queryParams query parameters
     * @return paginated list of products
     */
    List<SpuPageVO> listPagedSpuForApp(Page<SpuPageVO> page, SpuPageQuery queryParams);

}
