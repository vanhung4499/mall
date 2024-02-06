package com.hnv99.mall.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PmsBrand implements Serializable {
    private Long id;

    private String name;

    @ApiModelProperty(value = "First letter")
    private String firstLetter;

    private Integer sort;

    @ApiModelProperty(value = "Is it a brand manufacturer: 0->No; 1->Yes")
    private Integer factoryStatus;

    private Integer showStatus;

    @ApiModelProperty(value = "Product quantity")
    private Integer productCount;

    @ApiModelProperty(value = "Product comment count")
    private Integer productCommentCount;

    @ApiModelProperty(value = "Brand logo")
    private String logo;

    @ApiModelProperty(value = "Special area large picture")
    private String bigPic;

    @ApiModelProperty(value = "Brand story")
    private String brandStory;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                "Hash = " + hashCode() +
                ", id=" + id +
                ", name=" + name +
                ", firstLetter=" + firstLetter +
                ", sort=" + sort +
                ", factoryStatus=" + factoryStatus +
                ", showStatus=" + showStatus +
                ", productCount=" + productCount +
                ", productCommentCount=" + productCommentCount +
                ", logo=" + logo +
                ", bigPic=" + bigPic +
                ", brandStory=" + brandStory +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}