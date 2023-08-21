package ${entityPackage};

import com.ares.core.persistence.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Data
@Schema(name = "${entityName}对象",description = "")
public class ${entityName} extends BaseModel{
<#list columns as column>
    @Schema(description = "")
    private ${column.type} ${column.name};
</#list>
}
