package ${entityPackage};

import com.ares.core.persistence.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "${entityName}对象",description = "")
public class ${entityName} extends BaseModel{
<#list columns as column>
    @ApiModelProperty("")
    private ${column.type} ${column.name};
</#list>
}
