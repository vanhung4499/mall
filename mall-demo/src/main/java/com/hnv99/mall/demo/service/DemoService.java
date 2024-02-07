package com.hnv99.mall.demo.service;

import com.hnv99.mall.demo.dto.PmsBrandDto;
import com.hnv99.mall.model.PmsBrand;

import java.util.List;

public interface DemoService {
    List<PmsBrand> listAllBrand();

    int createBrand(PmsBrandDto pmsBrandDto);

    int updateBrand(Long id, PmsBrandDto pmsBrandDto);

    int deleteBrand(Long id);

    List<PmsBrand> listBrand(int pageNum, int pageSize);

    PmsBrand getBrand(Long id);
}
