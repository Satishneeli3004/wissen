package mavenproject.modules.sys.service;

import mavenproject.modules.sys.model.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 保存用户角色
     *
     * @param uid
     * @param roleId
     */
    void saveUserRole(String uid, List<String> roleId);

    /**
     * 用户的角色
     *
     * @param uid
     * @return
     */
    Set<String> userRoleIds(String uid);
}
