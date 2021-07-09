package mavenproject.modules.framework.model;

import mavenproject.modules.framework.enums.StatusEnum;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jinghong
 */
@Data
public class BaseModel extends BaseEntity {

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    protected String createBy;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String updateBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;

    /**
     * 状态1正常0删除
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    protected StatusEnum delFlag;

}
