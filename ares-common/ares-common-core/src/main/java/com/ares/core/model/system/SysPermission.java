package com.ares.core.model.system;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Young 2020/01/25
 **/
@Data
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1662711144186899011L;
    private String id;
    private String roleId;
    private String menuId;
    private String perms;
}
