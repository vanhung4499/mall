package com.hnv99.mall.model;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

public class UmsMemberLevel implements Serializable {
    private Long id;

    private String name;

    private Integer growthPoint;

    @ApiModelProperty(value = "Whether it is the default level: 0->No; 1->Yes")
    private Integer defaultStatus;

    @ApiModelProperty(value = "Free freight standard")
    private BigDecimal freeFreightPoint;

    @ApiModelProperty(value = "Growth value obtained each time a comment is made")
    private Integer commentGrowthPoint;

    @ApiModelProperty(value = "Whether there is a free postage privilege")
    private Integer privilegeFreeFreight;

    @ApiModelProperty(value = "Whether there is a sign-in privilege")
    private Integer privilegeSignIn;

    @ApiModelProperty(value = "Whether there is a comment reward privilege")
    private Integer privilegeComment;

    @ApiModelProperty(value = "Whether there is an exclusive event privilege")
    private Integer privilegePromotion;

    @ApiModelProperty(value = "Whether there is a member price privilege")
    private Integer privilegeMemberPrice;

    @ApiModelProperty(value = "Whether there is a birthday privilege")
    private Integer privilegeBirthday;

    private String note;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrowthPoint() {
        return growthPoint;
    }

    public void setGrowthPoint(Integer growthPoint) {
        this.growthPoint = growthPoint;
    }

    public Integer getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(Integer defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public BigDecimal getFreeFreightPoint() {
        return freeFreightPoint;
    }

    public void setFreeFreightPoint(BigDecimal freeFreightPoint) {
        this.freeFreightPoint = freeFreightPoint;
    }

    public Integer getCommentGrowthPoint() {
        return commentGrowthPoint;
    }

    public void setCommentGrowthPoint(Integer commentGrowthPoint) {
        this.commentGrowthPoint = commentGrowthPoint;
    }

    public Integer getPrivilegeFreeFreight() {
        return privilegeFreeFreight;
    }

    public void setPrivilegeFreeFreight(Integer privilegeFreeFreight) {
        this.privilegeFreeFreight = privilegeFreeFreight;
    }

    public Integer getPrivilegeSignIn() {
        return privilegeSignIn;
    }

    public void setPrivilegeSignIn(Integer privilegeSignIn) {
        this.privilegeSignIn = privilegeSignIn;
    }

    public Integer getPrivilegeComment() {
        return privilegeComment;
    }

    public void setPrivilegeComment(Integer privilegeComment) {
        this.privilegeComment = privilegeComment;
    }

    public Integer getPrivilegePromotion() {
        return privilegePromotion;
    }

    public void setPrivilegePromotion(Integer privilegePromotion) {
        this.privilegePromotion = privilegePromotion;
    }

    public Integer getPrivilegeMemberPrice() {
        return privilegeMemberPrice;
    }

    public void setPrivilegeMemberPrice(Integer privilegeMemberPrice) {
        this.privilegeMemberPrice = privilegeMemberPrice;
    }

    public Integer getPrivilegeBirthday() {
        return privilegeBirthday;
    }

    public void setPrivilegeBirthday(Integer privilegeBirthday) {
        this.privilegeBirthday = privilegeBirthday;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", growthPoint=").append(growthPoint);
        sb.append(", defaultStatus=").append(defaultStatus);
        sb.append(", freeFreightPoint=").append(freeFreightPoint);
        sb.append(", commentGrowthPoint=").append(commentGrowthPoint);
        sb.append(", privilegeFreeFreight=").append(privilegeFreeFreight);
        sb.append(", privilegeSignIn=").append(privilegeSignIn);
        sb.append(", privilegeComment=").append(privilegeComment);
        sb.append(", privilegePromotion=").append(privilegePromotion);
        sb.append(", privilegeMemberPrice=").append(privilegeMemberPrice);
        sb.append(", privilegeBirthday=").append(privilegeBirthday);
        sb.append(", note=").append(note);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}