package mavenproject.modules.sys.model.entity;

import mavenproject.modules.framework.model.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jinghong 2019/10/17
 */
@Data
@NoArgsConstructor
public class SysRoleMenu extends BaseEntity {

    private String roleId;
    private String menuId;

    public SysRoleMenu(String roleId, String menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }
}
