package com.union.mall.pms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.pms.model.entity.PmsSpu;
import com.union.mall.pms.model.form.PmsSpuForm;
import com.union.mall.pms.model.query.SpuPageQuery;
import com.union.mall.pms.model.vo.*;

import java.util.List;


/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface SpuService extends IService<PmsSpu> {

    /**
     * Admin - Get paged list of SPUs.
     *
     * @param queryParams Query parameters for pagination and filtering.
     * @return Paged list of SPUs.
     */
    IPage<PmsSpuPageVO> listPagedSpu(SpuPageQuery queryParams);

    /**
     * "App"端 - Get paged list of SPUs.
     *
     * @param queryParams Query parameters for pagination and filtering.
     * @return Paged list of SPUs.
     */
    IPage<SpuPageVO> listPagedSpuForApp(SpuPageQuery queryParams);

    /**
     * Admin - Get details of a specific SPU.
     *
     * @param id SPU ID.
     * @return SPU details.
     */
    PmsSpuDetailVO getSpuDetail(Long id);

    /**
     * "App"端 - Get details of a specific SPU.
     *
     * @param spuId SPU ID.
     * @return SPU details.
     */
    SpuDetailVO getSpuDetailForApp(Long spuId);

    /**
     * Add a new SPU.
     *
     * @param formData Form data for the new SPU.
     * @return `true` if the SPU was added successfully, otherwise `false`.
     */
    boolean addSpu(PmsSpuForm formData);

    /**
     * Update an existing SPU by ID.
     *
     * @param spuId    SPU ID.
     * @param formData Form data for updating the SPU.
     * @return `true` if the SPU was updated successfully, otherwise `false`.
     */
    boolean updateSpuById(Long spuId, PmsSpuForm formData);

    /**
     * Remove SPUs by their IDs.
     *
     * @param ids Comma-separated string of SPU IDs.
     * @return `true` if the SPUs were removed successfully, otherwise `false`.
     */
    boolean removeBySpuIds(String ids);

    /**
     * Get a list of seckilling SPUs.
     *
     * @return List of seckilling SPUs.
     */
    List<SeckillingSpuVO> listSeckillingSpu();
}
