package com.union.mall.system.model.bo;

import lombok.Data;

import java.util.Set;

/**
 * Role permission business object
 *
 * Represents the business object for role permissions,
 * including attributes such as role code and a set of permission identifiers.
 *
 * @author vanhung4499
 */
@Data
public class RolePermsBO {

    /**
     * Role code
     */
    private String roleCode;

    /**
     * Set of permission identifiers
     */
    private Set<String> perms;

}
