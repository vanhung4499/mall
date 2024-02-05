package com.hnv99.mall.common.api;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class CommonPage<T> {
    /**
     * Current page number
     */
    private Integer pageNum;
    /**
     * Number of items per page
     */
    private Integer pageSize;
    /**
     * Total number of pages
     */
    private Integer totalPage;
    /**
     * Total number of items
     */
    private Long total;
    /**
     * Paginated data
     */
    private List<T> list;

    /**
     * Converts the list paginated by PageHelper into pagination information
     */
    public static <T> CommonPage<T> restPage(List<T> list) {
        CommonPage<T> result = new CommonPage<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    /**
     * Converts the list paginated by SpringData into pagination information
     */
    public static <T> CommonPage<T> restPage(Page<T> pageInfo) {
        CommonPage<T> result = new CommonPage<T>();
        result.setTotalPage(pageInfo.getTotalPages());
        result.setPageNum(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotalElements());
        result.setList(pageInfo.getContent());
        return result;
    }
}

