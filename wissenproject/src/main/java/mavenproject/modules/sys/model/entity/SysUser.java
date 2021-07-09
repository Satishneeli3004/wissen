package mavenproject.modules.sys.model.entity;

import mavenproject.modules.framework.model.BaseModel;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author jinghong 2019/10/17
 */
@Data
public class SysUser extends BaseModel implements UserDetails {

    /**
     * 用户名
     */
    private String username;

    /**
     * 名称
     */
    @TableField(condition = SqlCondition.LIKE)
    private String name;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色
     */
    @TableField(exist = false)
    private Set<String> roles;

    /**
     * 没有过期
     */
    @TableField(exist = false)
    private boolean accountNonExpired = true;

    /**
     * 没有被锁定
     */
    @TableField(exist = false)
    private boolean accountNonLocked = true;

    /**
     * 密码没有过期
     */
    @TableField(exist = false)
    private boolean credentialsNonExpired = true;

    /**
     * 启用
     */
    @TableField(exist = false)
    private boolean enabled = true;

    /**
     * 权限集合
     */
    @TableField(exist = false)
    private Collection<? extends GrantedAuthority> authorities;
}
