package mavenproject.modules.sys.controller;

import cn.hutool.core.lang.Assert;
import mavenproject.common.annotation.Log;
import mavenproject.common.utils.R;
import mavenproject.modules.framework.controller.BaseController;
import mavenproject.modules.framework.enums.MenuTypeEnum;
import mavenproject.modules.framework.model.ComboBoxEntity;
import mavenproject.modules.sys.model.entity.SysMenu;
import mavenproject.modules.sys.service.SysMenuService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单管理
 *
 * @author jinghong 2019/10/17
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@Api(value = "菜单管理服务", tags = {"菜单管理"})
public class MenuController extends BaseController {

    private final SysMenuService sysMenuService;

    @GetMapping
    @PreAuthorize("hasAuthority('menu:view')")
    @ApiOperation(value = "分页列表", notes = "分页列表")
    public R menus(SysMenu menu) {
        List<SysMenu> list = sysMenuService.list(Wrappers.lambdaQuery(menu));
        return R.ok(list);
    }

    @GetMapping("/combobox")
    public R combobox() {
        List<SysMenu> list = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery()
                .select(SysMenu::getId, SysMenu::getName)
                .eq(SysMenu::getType, MenuTypeEnum.MENU));

        List<ComboBoxEntity> result = list.stream()
                .map(sysMenu -> new ComboBoxEntity(sysMenu.getId(), sysMenu.getName()))
                .collect(Collectors.toList());
        return R.ok(result);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('menu:view')")
    @ApiOperation(value = "根据ID获取详情", notes = "根据ID获取详情")
    public R menus(@PathVariable String id) {
        return R.ok(sysMenuService.getById(id));
    }

    @PostMapping
    @Log("新增菜单")
    @PreAuthorize("hasAuthority('menu:add')")
    @ApiOperation(value = "新增", notes = "新增")
    public R install(@Valid @RequestBody SysMenu sysMenu) {
        boolean save = sysMenuService.save(sysMenu);
        Assert.isTrue(save, "保存失败");
        return R.ok("保存成功");
    }

    @PutMapping("/{id}")
    @Log("修改菜单")
    @PreAuthorize("hasAuthority('menu:edit')")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@PathVariable String id, @Valid @RequestBody SysMenu sysMenu) {
        sysMenu.setId(id);
        boolean update = sysMenuService.updateById(sysMenu);
        Assert.isTrue(update, "修改失败");
        return R.ok("修改成功");
    }

    @DeleteMapping("/{id}")
    @Log("删除菜单")
    @PreAuthorize("hasAuthority('menu:del')")
    @ApiOperation(value = "删除", notes = "删除")
    public R del(@PathVariable String id) {
        boolean b = sysMenuService.removeById(id);
        Assert.isTrue(b, "删除失败");
        return R.ok("删除成功");
    }

}
