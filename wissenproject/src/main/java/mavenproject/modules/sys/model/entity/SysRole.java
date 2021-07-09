package mavenproject.modules.sys.model.entity;

import mavenproject.modules.framework.model.BaseModel;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author jinghong 2019/10/17
 */
@Data
public class SysRole extends BaseModel {

    /**
     * 角色名称
     */
    @TableField(condition = SqlCondition.LIKE)
    private String name;

    /**
     * 备注
     */
    private String comments;
}
