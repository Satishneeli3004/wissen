package mavenproject.modules.sys.model.parm;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author jinghong 2019/10/17
 */
@Data
public class SysUserPasswordParm {

    /**
     * 旧密码
     */
    @NotBlank(message = "旧密码不能为空")
    private String oldPsw;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPsw;
}
