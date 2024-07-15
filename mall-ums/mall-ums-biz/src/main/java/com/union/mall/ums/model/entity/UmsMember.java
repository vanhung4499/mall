package com.union.mall.ums.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.union.mall.common.core.base.BaseEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class UmsMember extends BaseEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer gender;

    private String nickName;

    private String mobile;

    private LocalDate birthday;

    private String avatarUrl;

    private String openid;

    private String sessionKey;

    private String city;

    private String country;

    private String language;

    private String province;

    private Integer status;

    private Long balance;

    @TableLogic(delval = "1", value = "0")
    private Integer deleted;

    @TableField(exist = false)
    private List<UmsAddress> addressList;

    private Integer point;

}
