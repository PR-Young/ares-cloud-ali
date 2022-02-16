package com.ares.flowable.persistence.dao;


import com.ares.core.dao.IBaseDao;
import com.ares.flowable.persistence.model.SysDeployForm;
import com.ares.flowable.persistence.model.SysForm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISysDeployFormDao extends IBaseDao<SysDeployForm> {
    /**
     * 查询流程实例关联表单
     *
     * @param id 流程实例关联表单ID
     * @return 流程实例关联表单
     */
    public SysDeployForm selectSysDeployFormById(String id);

    /**
     * 查询流程实例关联表单列表
     *
     * @param SysDeployForm 流程实例关联表单
     * @return 流程实例关联表单集合
     */
    public List<SysDeployForm> selectSysDeployFormList(SysDeployForm SysDeployForm);


    /**
     * 查询流程挂着的表单
     *
     * @param deployId
     * @return
     */
    SysForm selectSysDeployFormByDeployId(String deployId);
}
