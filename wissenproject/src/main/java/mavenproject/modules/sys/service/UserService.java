package mavenproject.modules.sys.service;

import mavenproject.modules.sys.model.dto.SysUserMenu;
import mavenproject.modules.sys.model.entity.SysUser;
import mavenproject.modules.sys.model.parm.SysUserParm;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author jinghong 2019/10/17
 */
public interface UserService extends IService<SysUser>, UserDetailsService {

    /**
     * 根据用户ID查询用户所有的菜单
     *
     * @param uid 用户ID
     * @return
     */
    List<SysUserMenu> userMenus(String uid);

    /**
     * 新增
     *
     * @param user
     */
    void install(SysUserParm user);

    /**
     * 修改用户
     *
     * @param user
     */
    void updateUser(SysUserParm user);

    /**
     * 用户的所有权限
     *
     * @param uid
     * @return
     */
    List<String> userAuthority(String uid);
}
