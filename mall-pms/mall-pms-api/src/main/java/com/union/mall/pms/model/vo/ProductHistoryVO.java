package com.union.mall.pms.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductHistoryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String picUrl;
}
