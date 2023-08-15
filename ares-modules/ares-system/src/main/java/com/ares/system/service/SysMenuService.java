/*
 *
 *  *  ******************************************************************************
 *  *  * Copyright (c) 2021 - 9999, ARES
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *  *****************************************************************************
 *
 */

package com.ares.system.service;


import com.ares.core.model.system.MetaVo;
import com.ares.core.model.system.RouterVo;
import com.ares.core.model.system.SysMenu;
import com.ares.core.model.system.SysUser;
import com.ares.core.model.tree.TreeSelect;
import com.ares.core.service.BaseService;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.system.dao.ISysMenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Young 2020/01/23
 **/
@Service
public class SysMenuService implements BaseService<SysMenu> {

    private ISysMenuDao sysMenuDao;

    @Autowired
    public SysMenuService(ISysMenuDao sysMenuDao) {
        this.sysMenuDao = sysMenuDao;
    }

    /**
     * 根据用户id获取用户角色如果用户为null 获取所有权限
     *
     * @return
     */
    public List<SysMenu> getAll(Long userId) {
        return sysMenuDao.getMenuByUserId(userId);
    }

    public List<SysMenu> list(Map<String, Object> map) {
        return sysMenuDao.list(map);
    }

    @Override
    public void insert(SysMenu obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        sysMenuDao.insert(obj);
    }

    @Override
    public void update(SysMenu obj) {
        obj.setModifyTime(new Date());
        sysMenuDao.update(obj);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        sysMenuDao.deleteByIds(ids);
    }

    @Override
    public SysMenu getById(Long id) {
        return sysMenuDao.getById(id);
    }

    public SysMenu getByPId(Long pid) {
        return sysMenuDao.getByPId(pid);
    }


    public List<RouterVo> buildMenus(List<SysMenu> menus, Long parentId) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            if (2 != menu.getType() && parentId.equals(menu.getPId())) {
                router.setHidden(menu.getVisible() == 1);
                router.setName(menu.getName());
                router.setPath(getRouterPath(menu));
                router.setComponent(("#").equals(menu.getUrl()) ? "Layout" : menu.getUrl().substring(1));
                router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
                if (menu.getChildCount() > 0) {
                    router.setAlwaysShow(true);
                    router.setRedirect("noRedirect");
                    router.setChildren(buildMenus(menus, menu.getId()));
                }
                routers.add(router);
            }
        }
        return routers;
    }

    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 一级目录
        if ("0".equals(menu.getPId())) {
            routerPath = "/" + menu.getPath();
        }
        return routerPath;
    }

    public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
        List<SysMenu> menuList = null;
        if (SysUser.isAdmin(userId)) {
            menuList = sysMenuDao.selectList(menu);
        } else {
            menu.getParams().put("userId", userId);
            menuList = sysMenuDao.selectListByUser(menu);
        }
        return menuList;
    }

    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext(); ) {
            SysMenu t = (SysMenu) iterator.next();
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if ("0".equals(t.getPId())) {
                recursionFn(menus, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<SysMenu> it = childList.iterator();
                while (it.hasNext()) {
                    SysMenu n = (SysMenu) it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (n.getPId().equals(t.getId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    public void remove(Long menuId) {
        sysMenuDao.deleteById(menuId);
    }

    public boolean hasChildByMenuId(Long menuId) {
        int result = sysMenuDao.hasChildByMenuId(menuId);
        return result > 0 ? true : false;
    }

    public List<String> selectMenuByRole(Long roleId) {
        return sysMenuDao.getMenuByRole(roleId);
    }

}
