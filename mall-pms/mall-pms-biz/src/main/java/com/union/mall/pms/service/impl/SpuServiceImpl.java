package com.union.mall.pms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.security.util.SecurityUtils;
import com.union.mall.pms.constant.ProductConstants;
import com.union.mall.pms.converter.SpuAttributeConverter;
import com.union.mall.pms.converter.SpuConverter;
import com.union.mall.pms.enums.AttributeTypeEnum;
import com.union.mall.pms.mapper.PmsSpuMapper;
import com.union.mall.pms.model.entity.PmsSku;
import com.union.mall.pms.model.entity.PmsSpu;
import com.union.mall.pms.model.entity.PmsSpuAttribute;
import com.union.mall.pms.model.form.PmsSpuAttributeForm;
import com.union.mall.pms.model.form.PmsSpuForm;
import com.union.mall.pms.model.query.SpuPageQuery;
import com.union.mall.pms.model.vo.*;
import com.union.mall.pms.service.SkuService;
import com.union.mall.pms.service.SpuAttributeService;
import com.union.mall.pms.service.SpuService;
import com.union.mall.ums.api.MemberFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SpuServiceImpl extends ServiceImpl<PmsSpuMapper, PmsSpu> implements SpuService {

    private final SkuService skuService;
    private final SpuAttributeService spuAttributeService;
    private final MemberFeignClient memberFeignClient;
    private final SpuConverter spuConverter;
    private final SpuAttributeConverter spuAttributeConverter;

    /**
     * Admin-Product Pagination List
     *
     * @param queryParams Query parameters
     * @return Product pagination list IPage<PmsSpuPageVO>
     */
    @Override
    public IPage<PmsSpuPageVO> listPagedSpu(SpuPageQuery queryParams) {
        Page<PmsSpuPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<PmsSpuPageVO> list = this.baseMapper.listPagedSpu(page, queryParams);
        page.setRecords(list);
        return page;
    }

    /**
     * APP-Product Pagination List
     *
     * @param queryParams Query parameters
     * @return Product pagination list IPage<SpuPageVO>
     */
    @Override
    public IPage<SpuPageVO> listPagedSpuForApp(SpuPageQuery queryParams) {
        Page<SpuPageVO> page = new Page<>(queryParams.getPageNum(), queryParams.getPageSize());
        List<SpuPageVO> list = this.baseMapper.listPagedSpuForApp(page, queryParams);
        page.setRecords(list);
        return page;
    }

    /**
     * App-Get Product Details
     *
     * @param spuId Product ID
     * @return Product details
     */
    @Override
    public SpuDetailVO getSpuDetailForApp(Long spuId) {
        PmsSpu pmsSpu = this.getById(spuId);
        Assert.isTrue(pmsSpu != null, "Product does not exist");

        SpuDetailVO spuDetailVO = new SpuDetailVO();

        // Product basic information
        SpuDetailVO.GoodsInfo goodsInfo = new SpuDetailVO.GoodsInfo();
        BeanUtil.copyProperties(pmsSpu, goodsInfo, "album");

        List<String> album = new ArrayList<>();

        if (StrUtil.isNotBlank(pmsSpu.getPicUrl())) {
            album.add(pmsSpu.getPicUrl());
        }
        if (pmsSpu.getAlbum() != null && pmsSpu.getAlbum().length > 0) {
            album.addAll(Arrays.asList(pmsSpu.getAlbum()));
            goodsInfo.setAlbum(album);
        }
        spuDetailVO.setGoodsInfo(goodsInfo);

        // Product attribute list
        List<SpuDetailVO.Attribute> attributeList = spuAttributeService.list(new LambdaQueryWrapper<PmsSpuAttribute>()
                        .eq(PmsSpuAttribute::getType, AttributeTypeEnum.ATTR.getValue()).eq(PmsSpuAttribute::getSpuId, spuId)
                        .select(PmsSpuAttribute::getId, PmsSpuAttribute::getName, PmsSpuAttribute::getValue)).stream()
                .map(item -> {
                    SpuDetailVO.Attribute attribute = new SpuDetailVO.Attribute();
                    BeanUtil.copyProperties(item, attribute);
                    return attribute;
                }).collect(Collectors.toList());
        spuDetailVO.setAttributeList(attributeList);

        // Product specification list
        List<PmsSpuAttribute> specSourceList = spuAttributeService.list(new LambdaQueryWrapper<PmsSpuAttribute>()
                .eq(PmsSpuAttribute::getType, AttributeTypeEnum.SPEC.getValue())
                .eq(PmsSpuAttribute::getSpuId, spuId)
                .select(PmsSpuAttribute::getId, PmsSpuAttribute::getName, PmsSpuAttribute::getValue));

        List<SpuDetailVO.Specification> specList = new ArrayList<>();
        // Specification Map [key:"Color", value:[{id:1, value:"Black"},{id:2, value:"White"}]]
        Map<String, List<PmsSpuAttribute>> specValueMap = specSourceList.stream().collect(Collectors.groupingBy(PmsSpuAttribute::getName));

        for (Map.Entry<String, List<PmsSpuAttribute>> entry : specValueMap.entrySet()) {
            String specName = entry.getKey();
            List<PmsSpuAttribute> specValueSourceList = entry.getValue();

            // Specification mapping handling
            SpuDetailVO.Specification spec = new SpuDetailVO.Specification();
            spec.setName(specName);
            if (CollectionUtil.isNotEmpty(specValueSourceList)) {
                List<SpuDetailVO.Specification.Value> specValueList = specValueSourceList.stream().map(item -> {
                    SpuDetailVO.Specification.Value specValue = new SpuDetailVO.Specification.Value();
                    specValue.setId(item.getId());
                    specValue.setValue(item.getValue());
                    return specValue;
                }).collect(Collectors.toList());
                spec.setValues(specValueList);
                specList.add(spec);
            }
        }
        spuDetailVO.setSpecList(specList);

        // Product SKU list
        List<PmsSku> skuSourceList = skuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));
        if (CollectionUtil.isNotEmpty(skuSourceList)) {
            List<SpuDetailVO.Sku> skuList = skuSourceList.stream().map(item -> {
                SpuDetailVO.Sku sku = new SpuDetailVO.Sku();
                BeanUtil.copyProperties(item, sku);
                return sku;
            }).collect(Collectors.toList());
            spuDetailVO.setSkuList(skuList);
        }

        // Add user browsing history
        Long memberId = SecurityUtils.getMemberId();
        if (memberId != null) {
            ProductHistoryVO vo = new ProductHistoryVO();
            vo.setId(goodsInfo.getId());
            vo.setName(goodsInfo.getName());
            vo.setPicUrl(goodsInfo.getAlbum() != null ? goodsInfo.getAlbum().get(0) : null);
            memberFeignClient.addProductViewHistory(vo);
        }
        return spuDetailVO;
    }

    /**
     * Get product details
     *
     * @param spuId Product ID
     * @return Product details
     */
    @Override
    public PmsSpuDetailVO getSpuDetail(Long spuId) {
        PmsSpuDetailVO pmsSpuDetailVO = new PmsSpuDetailVO();

        PmsSpu entity = this.getById(spuId);
        Assert.isTrue(entity != null, "Product does not exist");

        BeanUtil.copyProperties(entity, pmsSpuDetailVO, "album");
        pmsSpuDetailVO.setSubPicUrls(entity.getAlbum());

        // Product attribute list
        List<PmsSpuAttribute> attrList = spuAttributeService.list(new LambdaQueryWrapper<PmsSpuAttribute>()
                .eq(PmsSpuAttribute::getSpuId, spuId)
                .eq(PmsSpuAttribute::getType, AttributeTypeEnum.ATTR.getValue()));
        pmsSpuDetailVO.setAttrList(attrList);

        // Product specification list
        List<PmsSpuAttribute> specList = spuAttributeService.list(new LambdaQueryWrapper<PmsSpuAttribute>()
                .eq(PmsSpuAttribute::getSpuId, spuId)
                .eq(PmsSpuAttribute::getType, AttributeTypeEnum.SPEC.getValue()));
        pmsSpuDetailVO.setSpecList(specList);

        // Product SKU list
        List<PmsSku> skuList = skuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));
        pmsSpuDetailVO.setSkuList(skuList);
        return pmsSpuDetailVO;
    }

    /**
     * Add product
     *
     * @param formData Product form
     * @return Whether successful
     */
    @Override
    @Transactional
    public boolean addSpu(PmsSpuForm formData) {

        PmsSpu entity = spuConverter.form2Entity(formData);

        boolean result = this.save(entity);
        if (result) {
            Long spuId = entity.getId();
            // Save attributes
            List<PmsSpuAttributeForm> attrList = formData.getAttrList();
            this.saveSpuAttrs(spuId, attrList);
            // Save specifications
            List<PmsSpuAttributeForm> specList = formData.getSpecList();
            Map<String, Long> tempWithNewSpecIdMap = this.saveSpuSpecs(spuId, specList);
            // Save SKU
            List<PmsSku> skuList = formData.getSkuList();
            this.saveSku(spuId, skuList, tempWithNewSpecIdMap);

        }
        // Return true if no exceptions
        return result;
    }

    /**
     * Update product
     *
     * @param spuId    Product ID
     * @param formData Product form
     * @return Whether successful
     */
    @Transactional
    @Override
    public boolean updateSpuById(Long spuId, PmsSpuForm formData) {

        PmsSpu entity = spuConverter.form2Entity(formData);

        boolean result = this.updateById(entity);
        if (result) {

            // Save attributes
            List<PmsSpuAttributeForm> attrList = formData.getAttrList();
            this.saveSpuAttrs(spuId, attrList);

            // Save product specifications
            List<PmsSpuAttributeForm> specList = formData.getSpecList();
            Map<String, Long> specTempIdIdMap = this.saveSpuSpecs(spuId, specList);

            // Save SKU
            List<PmsSku> skuList = formData.getSkuList();
            this.saveSku(spuId, skuList, specTempIdIdMap);
        }

        return result;
    }

    /**
     * Delete product
     *
     * @param ids Product IDs, separated by commas (,)
     * @return Whether successful
     */
    @Override
    @Transactional
    public boolean removeBySpuIds(String ids) {

        String[] spuIds = ids.split(",");

        for (String spuId : spuIds) {
            skuService.remove(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId));
            // Specifications
            spuAttributeService.remove(new LambdaQueryWrapper<PmsSpuAttribute>().eq(PmsSpuAttribute::getSpuId, spuId));
            // Attributes
            spuAttributeService.remove(new LambdaQueryWrapper<PmsSpuAttribute>().eq(PmsSpuAttribute::getSpuId, spuId));
            // SPU
            this.removeById(spuId);
        }
        // Return true if no exceptions
        return true;
    }

    /**
     * Get product seckill interface
     *
     * @return Product seckill list
     */
    @Override
    public List<SeckillingSpuVO> listSeckillingSpu() {
        List<PmsSpu> entities = this.list(new LambdaQueryWrapper<PmsSpu>()
                .select(PmsSpu::getId, PmsSpu::getName, PmsSpu::getPicUrl, PmsSpu::getPrice)
                .orderByDesc(PmsSpu::getCreateTime)
        );
        return spuConverter.entity2SeckillingVO(entities);
    }


    /**
     * Save SKU, replacing temporary specification IDs in the submitted form with persistent database IDs
     *
     * @param spuId           Product ID
     * @param skuList         SKU list
     * @param specTempIdIdMap Mapping of temporary specification IDs and persistent database IDs
     * @return Whether successful
     */
    private boolean saveSku(Long spuId, List<PmsSku> skuList, Map<String, Long> specTempIdIdMap) {

        // Delete SKUs
        List<Long> formSkuIds = skuList.stream().map(PmsSku::getId).collect(Collectors.toList());

        List<Long> dbSkuIds = skuService.list(new LambdaQueryWrapper<PmsSku>().eq(PmsSku::getSpuId, spuId)
                        .select(PmsSku::getId)).stream().map(PmsSku::getId)
                .collect(Collectors.toList());

        List<Long> removeSkuIds = dbSkuIds.stream().filter(dbSkuId -> !formSkuIds.contains(dbSkuId)).collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(removeSkuIds)) {
            skuService.removeByIds(removeSkuIds);
        }

        // Add/update SKUs
        List<PmsSku> pmsSkuList = skuList.stream().map(sku -> {
            // Convert temporary specification IDs
            String specIds = Arrays.stream(sku.getSpecIds().split("\\|"))
                    .map(specId -> specId.startsWith(ProductConstants.SPEC_TEMP_ID_PREFIX) ? specTempIdIdMap.get(specId) + "" : specId)
                    .collect(Collectors.joining("_"));
            sku.setSpecIds(specIds);
            sku.setSpuId(spuId);
            return sku;
        }).collect(Collectors.toList());
        return skuService.saveOrUpdateBatch(pmsSkuList);
    }


    /**
     * Save product attributes
     *
     * @param spuId    Product ID
     * @param attrList Product attribute list
     * @return Whether successful
     */
    private boolean saveSpuAttrs(Long spuId, List<PmsSpuAttributeForm> attrList) {

        // 1. [Delete] Product attributes removed in this submission

        // 1.1 Product attribute IDs retained in this submission
        List<Long> retainAttrIds = attrList.stream()
                .filter(item -> item.getId() != null)
                .map(item -> Convert.toLong(item.getId()))
                .collect(Collectors.toList());
        // 1.2 Get original product attribute ID collection
        List<Long> originAttrIds = spuAttributeService.list(new LambdaQueryWrapper<PmsSpuAttribute>()
                        .eq(PmsSpuAttribute::getSpuId, spuId).eq(PmsSpuAttribute::getType, AttributeTypeEnum.ATTR.getValue())
                        .select(PmsSpuAttribute::getId))
                .stream()
                .map(PmsSpuAttribute::getId)
                .collect(Collectors.toList());
        // 1.3 Product attributes to be deleted: original product attributes - attributes retained in this submission
        List<Long> removeAttrValIds = originAttrIds.stream()
                .filter(id -> !retainAttrIds.contains(id))
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(removeAttrValIds)) {
            spuAttributeService.removeByIds(removeAttrValIds);
        }

        // Add or update product attributes
        List<PmsSpuAttribute> entities = attrList.stream().map(item -> {
            PmsSpuAttribute entity = spuAttributeConverter.form2Entity(item);
            entity.setId(Convert.toLong(item.getId()));
            entity.setSpuId(spuId);
            entity.setType(AttributeTypeEnum.ATTR.getValue());
            return entity;
        }).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(entities)) {
            return spuAttributeService.saveOrUpdateBatch(entities);
        }
        return true;
    }

    /**
     * Save product specifications
     * <p>
     * Newly added specifications need to return the mapping relationship between temporary IDs and persistent IDs stored in the database, replacing the specification IDs collection in SKU
     *
     * @param spuId    Product ID
     * @param specList Specification list
     * @return Map: key-temporary ID; value-persistent ID returned
     */
    private Map<String, Long> saveSpuSpecs(Long spuId, List<PmsSpuAttributeForm> specList) {


        // 1. [Delete] Product specifications removed in this submission
        // 1.1 Specifications retained in this submission
        List<Long> retainSpuSpecIds = specList.stream()
                .filter(item -> !item.getId().startsWith(ProductConstants.SPEC_TEMP_ID_PREFIX))
                .map(item -> Convert.toLong(item.getId()))
                .collect(Collectors.toList());

        // 1.2 Original product specifications
        List<Long> originSpuSpecIds = spuAttributeService.list(new LambdaQueryWrapper<PmsSpuAttribute>()
                        .eq(PmsSpuAttribute::getSpuId, spuId)
                        .eq(PmsSpuAttribute::getType, AttributeTypeEnum.SPEC.getValue())
                        .select(PmsSpuAttribute::getId))
                .stream().map(PmsSpuAttribute::getId)
                .collect(Collectors.toList());

        // 1.3 Product specifications to be deleted: original product specifications - specifications retained in this submission
        List<Long> removeSpuSpecIds = originSpuSpecIds.stream().filter(id -> !retainSpuSpecIds.contains(id))
                .collect(Collectors.toList());

        if (CollectionUtil.isNotEmpty(removeSpuSpecIds)) {
            // Delete product specifications
            spuAttributeService.removeByIds(removeSpuSpecIds);
        }

        // 2. [Add] Newly added product specifications in this submission
        // Mapping between temporary specification IDs and persistent IDs obtained from the database, used to replace temporary specification IDs in SKU
        Map<String, Long> tempWithNewSpecIdMap = new HashMap<>();
        List<PmsSpuAttributeForm> newSpecList = specList.stream()
                .filter(item -> item.getId().startsWith(ProductConstants.SPEC_TEMP_ID_PREFIX))
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(newSpecList)) {
            newSpecList.forEach(item -> {
                PmsSpuAttribute entity = spuAttributeConverter.form2Entity(item);
                entity.setSpuId(spuId);
                entity.setType(AttributeTypeEnum.SPEC.getValue());
                spuAttributeService.save(entity);
                tempWithNewSpecIdMap.put(item.getId(), entity.getId());
            });
        }

        // 3. [Update] Product specifications to be modified in this submission
        List<PmsSpuAttribute> pmsSpuAttributeList = specList.stream()
                .filter(item -> !item.getId().startsWith(ProductConstants.SPEC_TEMP_ID_PREFIX))
                .map(item -> {
                    PmsSpuAttribute entity = spuAttributeConverter.form2Entity(item);
                    entity.setId(Convert.toLong(item.getId()));
                    entity.setSpuId(spuId);
                    entity.setType(AttributeTypeEnum.SPEC.getValue());
                    return entity;
                }).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(pmsSpuAttributeList)) {
            spuAttributeService.updateBatchById(pmsSpuAttributeList);
        }
        return tempWithNewSpecIdMap;
    }

}
