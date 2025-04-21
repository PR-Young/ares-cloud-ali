package ${daoPackage};

import ${entityPackage}.${entityName};
import org.springframework.stereotype.Repository;

/**
* @description:
* @author: ${author}
* @date: ${createTime}
**/
@Repository
public interface I${entityName}Dao extends IBaseDao<${entityName}, ${entityName}Query>{

}
