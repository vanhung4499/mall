package com.union.mall.system.model.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.union.mall.system.enums.MenuTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class RouteBO {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * Parent menu ID
     */
    private Long parentId;

    /**
     * Menu name
     */
    private String name;

    /**
     * Menu type (1 - Menu; 2 - Directory; 3 - External link; 4 - Button permission)
     */
    private MenuTypeEnum type;

    /**
     * Route path (browser address bar path)
     */
    private String path;

    /**
     * Component path (full path of Vue page, without the .vue suffix)
     */
    private String component;

    /**
     * Permission identifier
     */
    private String perm;

    /**
     * Display status (1: Display; 0: Hide)
     */
    private Integer visible;

    /**
     * Sorting order
     */
    private Integer sort;

    /**
     * Menu icon
     */
    private String icon;

    /**
     * Redirect path
     */
    private String redirect;

    /**
     * Roles that have access to the route
     */
    private List<String> roles;

    /**
     * [Directory] Whether to always show when it has only one child route (1: Yes, 0: No)
     */
    private Integer alwaysShow;

    /**
     * [Menu] Whether to enable page caching (1: Yes, 0: No)
     */
    private Integer keepAlive;

}
