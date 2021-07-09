package mavenproject.modules.sys.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import mavenproject.common.annotation.Log;
import mavenproject.common.constant.SecurityConstants;
import mavenproject.common.security.util.SecurityUtils;
import mavenproject.common.utils.R;
import mavenproject.modules.framework.controller.BaseController;
import mavenproject.modules.sys.model.entity.SysUser;
import mavenproject.modules.sys.model.parm.SysUserParm;
import mavenproject.modules.sys.model.parm.SysUserPasswordParm;
import mavenproject.modules.sys.service.SysUserRoleService;
import mavenproject.modules.sys.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户管理
 *
 * @author jinghong 2019/10/17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Api(value = "用户接口服务", tags = {"用户接口服务"})
public class UserController extends BaseController {

    private final UserService        userService;
    private final SysUserRoleService userRoleService;
    private final PasswordEncoder    passwordEncoder;
    private final TokenStore         tokenStore;

    /**
     * 获取当前登录的用户信息
     *
     * @return
     */
    @GetMapping("/info")
    @ApiOperation(value = "获取当前登录的用户信息", notes = "获取当前登录的用户信息")
    public R info() {
        return R.ok(SecurityUtils.getUser());
    }

    /**
     * 当前登录人的所有菜单
     *
     * @return
     */
    @GetMapping("/menus")
    @ApiOperation(value = "当前登录人的所有菜单", notes = "当前登录人的所有菜单")
    public R menus() {
        return R.ok(userService.userMenus(SecurityUtils.getUser().getId()));
    }

    /**
     * 获取某个用户的角色
     *
     * @param id 用户ID
     * @return
     */
    @GetMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('user:view')")
    @ApiOperation(value = "获取某个用户的角色", notes = "获取某个用户的角色")
    public R roles(@PathVariable String id) {
        return R.ok(userRoleService.userRoleIds(id));
    }

    /**
     * 用户列表
     *
     * @param user
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAuthority('user:view')")
    @ApiOperation(value = "用户列表", notes = "用户列表")
    public R users(SysUser user) {
        IPage<SysUser> page = userService.page(getPage(), Wrappers.lambdaQuery(user).select(e -> !e.getColumn().equals("password")));
        return R.ok(page);
    }


    /**
     * 根据ID删除一个用户
     *
     * @param id 要删除的用户ID
     * @return
     */
    @Log("删除用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:del')")
    @ApiOperation(value = "根据ID删除一个用户", notes = "根据ID删除一个用户")
    public R del(@PathVariable String id) {
        boolean b = userService.removeById(id);
        Assert.isTrue(b, "删除失败");
        return R.ok();
    }

    /**
     * 新增一个用户
     *
     * @param user 用户信息
     * @return
     */
    @PostMapping
    @Log("新增用户")
    @PreAuthorize("hasAuthority('user:add')")
    @ApiOperation(value = "新增一个用户", notes = "新增一个用户")
    public R install(@Valid @RequestBody SysUserParm user) {
        userService.install(user);
        return R.ok();
    }


    /**
     * 修改用户
     *
     * @param user 要修改的信息
     * @param id   用户ID
     * @return
     */
    @PutMapping("/{id}")
    @Log("修改用户")
    @PreAuthorize("hasAuthority('user:edit')")
    @ApiOperation(value = "修改用户", notes = "修改用户")
    public R updateUser(@Valid @RequestBody SysUserParm user, @PathVariable String id) {
        user.setId(id);
        userService.updateUser(user);
        return R.ok();
    }

    /**
     * 重置某个用户的密码
     *
     * @param id 用户ID
     * @return
     */
    @Log("重置用户密码")
    @PutMapping("/reset-password/{id}")
    @PreAuthorize("hasAuthority('user:rest:password')")
    @ApiOperation(value = "重置某个用户的密码", notes = "重置某个用户的密码")
    public R resetPassword(@PathVariable String id) {
        Assert.notBlank(id, "用户ID不能为空");
        boolean update = userService.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getPassword, passwordEncoder.encode(SecurityConstants.DEFAULT_PASSWORD)).eq(SysUser::getId, id));
        Assert.isTrue(update, "修改失败");
        return R.ok("修改成功,新密码" + SecurityConstants.DEFAULT_PASSWORD);
    }

    /**
     * 重置自己的密码
     *
     * @param parm
     * @return
     */
    @Log("重置自己密码")
    @PutMapping("/reset-password")
    @ApiOperation(value = "重置自己的密码", notes = "重置自己的密码")
    public R updatePassword(@Valid @RequestBody SysUserPasswordParm parm) {
        String  id      = SecurityUtils.getUser().getId();
        SysUser user    = userService.getOne(Wrappers.<SysUser>lambdaQuery().select(SysUser::getPassword).eq(SysUser::getId, id));
        boolean matches = passwordEncoder.matches(parm.getOldPsw(), user.getPassword());
        Assert.isTrue(matches, "旧密码不正确");
        boolean update = userService.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getPassword, passwordEncoder.encode(parm.getNewPsw())).eq(SysUser::getId, id));
        Assert.isTrue(update, "修改失败");
        return R.ok("修改成功");
    }

    /**
     * 退出登录
     *
     * @return
     */
    @PostMapping("/logout")
    public R logout() {
        // 获取token
        Object accessTokenValue = request.getAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE);
        String tokenValue       = StrUtil.toString(accessTokenValue);

        // 获取OAuth2AccessToken
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);

        // 不为空则删除token和refreshToken
        if (accessToken != null) {
            // 删除access token
            tokenStore.removeAccessToken(accessToken);

            // 删除refresh token
            tokenStore.removeRefreshToken(accessToken.getRefreshToken());
        }
        return R.ok("退出成功");
    }
}
