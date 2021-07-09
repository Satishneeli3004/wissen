package mavenproject.modules.sys.controller;

import mavenproject.common.utils.R;
import mavenproject.modules.framework.controller.BaseController;
import mavenproject.modules.sys.model.entity.SysDict;
import mavenproject.modules.sys.service.SysDictService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 字典管理
 *
 * @author jinghong 2019/10/17
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dict")
@Api(value = "字典管理", tags = "字典管理")
public class DictController extends BaseController {

    private final SysDictService dictService;

    /**
     * 根据类型获取字典值
     * /dict/sex
     *
     * @param type
     * @return
     */
    @GetMapping("/{type}")
    @ApiOperation(value = "根据类型获取字典值", notes = "根据类型获取字典值")
    public R type(@PathVariable String type) {
        return R.ok(dictService.type(type));
    }

    /**
     * 分页列表
     *
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("hasAuthority('dict:view')")
    @ApiOperation(value = "分页列表", notes = "分页列表")
    public R page(SysDict dict) {
        return R.ok(dictService.page(getPage(), Wrappers.lambdaQuery(dict).orderByDesc(SysDict::getUpdateTime, SysDict::getType)));
    }

    /**
     * 新增
     *
     * @param dict
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('dict:add')")
    @ApiOperation(value = "新增", notes = "新增")
    public R install(@Validated @RequestBody SysDict dict) {
        boolean save = dictService.save(dict);
        return save ? R.ok("保存成功") : R.failed("保存失败");
    }

    /**
     * 修改
     *
     * @param dict
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('dict:edit')")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@Validated @RequestBody SysDict dict, @PathVariable String id) {
        dict.setId(id);
        boolean update = dictService.updateById(dict);
        return update ? R.ok("修改成功") : R.failed("修改失败");
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('dict:del')")
    @ApiOperation(value = "删除", notes = "删除")
    public R del(@PathVariable String id) {
        boolean remove = dictService.removeById(id);
        return remove ? R.ok("删除成功") : R.failed("删除失败");
    }
}
