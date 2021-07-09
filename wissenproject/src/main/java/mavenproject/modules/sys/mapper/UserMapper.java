package mavenproject.modules.sys.mapper;

import mavenproject.modules.sys.model.dto.SysUserMenu;
import mavenproject.modules.sys.model.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户
 *
 * @author jinghong
 */
public interface UserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户ID查询用户所有的菜单
     *
     * @param uid 用户ID
     * @return
     */
    List<SysUserMenu> userMenus(@Param("uid") String uid);

    /**
     * 用户的所有权限
     *
     * @param uid
     * @return
     */
    List<String> userAuthority(@Param("uid") String uid);
}
