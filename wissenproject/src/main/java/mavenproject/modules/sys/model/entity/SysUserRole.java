package mavenproject.modules.sys.model.entity;

import mavenproject.modules.framework.model.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jinghong 2019/10/17
 */
@Data
@NoArgsConstructor
public class SysUserRole extends BaseEntity {

    private String userId;
    private String roleId;

    public SysUserRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
