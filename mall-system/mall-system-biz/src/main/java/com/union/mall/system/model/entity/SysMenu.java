package com.union.mall.system.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.union.mall.common.core.base.BaseEntity;
import com.union.mall.system.enums.MenuTypeEnum;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@TableName(value ="sys_menu")
@Data
public class SysMenu extends BaseEntity {
    /**
     * Menu ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Parent Menu ID
     */
    private Long parentId;

    /**
     * Menu Name
     */
    private String name;

    /**
     * Menu Type (1-Menu; 2-Directory; 3-External Link; 4-Button Permission)
     */
    private MenuTypeEnum type;

    /**
     * Route Path (Browser address bar path)
     */
    private String path;

    /**
     * Component Path (Full path of the vue page, omit the .vue suffix)
     */
    private String component;

    /**
     * Permission Identifier
     */
    private String perm;

    /**
     * Display Status (1: Display; 0: Hide)
     */
    private Integer visible;

    /**
     * Sort Order
     */
    private Integer sort;

    /**
     * Menu Icon
     */
    private String icon;

    /**
     * Redirect Path
     */
    private String redirect;

    /**
     * Parent Node Path, separated by English commas (,)
     */
    private String treePath;

    /**
     * [Menu] Enable Page Cache (1: Enable; 0: Disable)
     */
    private Integer keepAlive;

    /**
     * [Directory] Always Show if there is only one child route (1: Yes; 0: No)
     */
    private Integer alwaysShow;
}
