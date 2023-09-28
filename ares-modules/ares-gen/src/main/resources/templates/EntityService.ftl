package ${servicePackage};

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ${entityPackage}.${entityName};
import ${daoPackage}.I${entityName}Dao;
import com.ares.core.utils.SnowflakeIdWorker;
import com.ares.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ${entityName}Service implements BaseService<${entityName}>{

    private I${entityName}Dao ${entityName1}Dao;

    @Autowired
    public ${entityName}Service(I${entityName}Dao ${entityName1}Dao){
        this.${entityName1}Dao = ${entityName1}Dao;
    }

    public PageInfo<${entityName}> list(int pageNo, int pageSize, ${"Map<String, Object>"} map) {
        PageHelper.startPage(pageNo, pageSize);
        List<${entityName}> lists = ${entityName1}Dao.list(map);
        PageInfo<${entityName}> pageInfo = new PageInfo<>(lists);
        return pageInfo;
    }

    @Override
    public void insert(${entityName} obj) {
        obj.setId(SnowflakeIdWorker.getUUID());
        obj.setCreateTime(new Date());
        ${entityName1}Dao.insert(obj);
    }

    @Override
    public void update(${entityName} obj) {
        obj.setModifyTime(new Date());
        ${entityName1}Dao.update(obj);
    }

    @Override
    public void deleteByIds(${"List<String>"} ids) {
        ${entityName1}Dao.deleteByIds(ids);
    }

    @Override
    public ${entityName} getById(Long id) {
        return ${entityName1}Dao.getById(id);
    }

    public List<${entityName}> list(${entityName} obj) {
        List<${entityName}> lists = ${entityName1}Dao.selectList(obj);
        return lists;
    }

}
