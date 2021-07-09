package mavenproject.modules.sys.controller;

import mavenproject.common.annotation.Log;
import mavenproject.common.utils.R;
import mavenproject.modules.framework.controller.BaseController;
import mavenproject.modules.framework.model.FormSelects;
import mavenproject.modules.framework.model.ZtreeModel;
import mavenproject.modules.sys.model.dto.SysRoleDTO;
import mavenproject.modules.sys.model.entity.SysRole;
import mavenproject.modules.sys.model.parm.SysRoleMenuParm;
import mavenproject.modules.sys.service.SysRoleMenuService;
import mavenproject.modules.sys.service.SysRoleService;
import mavenproject.modules.sys.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色管理
 *
 * @author jinghong 2019/10/17
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "角色管理接口服务", tags = {"角色管理接口服务"})
public class RoleController extends BaseController {

    private final SysRoleService sysRoleService;
    private final SysUserRoleService userRoleService;
    private final SysRoleMenuService sysRoleMenuService;

    @GetMapping
    @PreAuthorize("hasAuthority('role:view')")
    @ApiOperation(value = "分页列表", notes = "分页列表")
    public R roles(SysRole role) {
        IPage<SysRole> page = sysRoleService.page(getPage(), Wrappers.lambdaQuery(role));
        return R.ok(page);
    }

    @GetMapping("/comboBox")
    public R comboBox() {
        List<SysRole> list = sysRoleService.list(Wrappers.<SysRole>lambdaQuery()
                .select(SysRole::getId, SysRole::getName));
        List<FormSelects> result = list.stream()
                .map(sysRole -> new FormSelects(sysRole.getName(), sysRole.getId(), "", ""))
                .collect(Collectors.toList());
        return R.ok(result);
    }

    @GetMapping("/comboBox/{id}")
    public R comboBox(@PathVariable String id) {
        List<SysRole> list = sysRoleService.list(Wrappers.<SysRole>lambdaQuery()
                .select(SysRole::getId, SysRole::getName));

        Set<String> roleIds = userRoleService.userRoleIds(id);

        List<FormSelects> result = list.stream()
                .map(sysRole -> new FormSelects(sysRole.getName(), sysRole.getId(), roleIds.contains(sysRole.getId()) ? "selected" : "", ""))
                .collect(Collectors.toList());
        return R.ok(result);
    }

    @PostMapping
    @Log("新增角色")
    @PreAuthorize("hasAuthority('role:add')")
    @ApiOperation(value = "新增", notes = "新增")
    public R install(@Valid @RequestBody SysRoleDTO role) {
        sysRoleService.save(role.convert(SysRole.class));
        return R.ok("添加成功");
    }

    @PutMapping("/{id}")
    @Log("修改角色")
    @PreAuthorize("hasAuthority('role:edit')")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Valid @RequestBody SysRoleDTO role, @PathVariable String id) {
        role.setId(id);
        sysRoleService.updateById(role.convert(SysRole.class));
        return R.ok("修改成功");
    }

    @DeleteMapping("/{id}")
    @Log("删除角色")
    @PreAuthorize("hasAuthority('role:del')")
    @ApiOperation(value = "根据ID删除", notes = "根据ID删除")
    public R del(@PathVariable String id) {
        sysRoleService.removeById(id);
        return R.ok("删除成功");
    }

    @GetMapping("authTree/{id}")
    public R authTree(@PathVariable String id) {
        List<ZtreeModel> result = sysRoleService.authTree(id);
        return R.ok(result);
    }

    @Log("修改角色权限")
    @PostMapping("menu/{id}")
    @PreAuthorize("hasAuthority('role:edit')")
    public R roleMenu(@PathVariable String id, @RequestBody SysRoleMenuParm parm) {
        parm.setId(id);
        sysRoleMenuService.saveRoleMenu(parm);
        return R.ok();
    }
}
