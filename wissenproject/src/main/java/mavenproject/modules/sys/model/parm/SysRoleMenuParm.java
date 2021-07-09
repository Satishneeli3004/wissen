package mavenproject.modules.sys.model.parm;

import mavenproject.modules.framework.model.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
public class SysRoleMenuParm extends BaseEntity {

    private List<String> menuIds;
}
