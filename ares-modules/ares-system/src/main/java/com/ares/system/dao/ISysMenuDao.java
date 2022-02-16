package com.ares.system.dao;


import com.ares.core.dao.IBaseDao;
import com.ares.core.model.system.SysMenu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: Young 2020/01/23
 **/
@Repository
public interface ISysMenuDao extends IBaseDao<SysMenu> {

    List<SysMenu> getMenuByUserId(String userId);

    SysMenu getByPId(String pid);

    List<String> getMenuByRole(String roleId);

    List<SysMenu> selectListByUser(SysMenu menu);

    int deleteById(String menuId);

    int hasChildByMenuId(String menuId);

}
