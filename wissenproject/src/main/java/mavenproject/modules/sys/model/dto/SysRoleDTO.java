package mavenproject.modules.sys.model.dto;

import mavenproject.modules.framework.model.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 角色信息
 *
 * @author jinghong 2019/10/17
 */
@Data
public class SysRoleDTO extends BaseEntity {

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String name;

    /**
     * 备注
     */
    private String comments;
}
