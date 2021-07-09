package mavenproject.modules.sys.model.parm;

import mavenproject.modules.framework.model.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author jinghong 2019/10/17
 */
@Data
@ApiModel(description = "用户参数")
public class SysUserParm extends BaseEntity {
    private static final long serialVersionUID = 1;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", example = "张三", required = true)
    private String username;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色")
    private List<String> roleId;
}
