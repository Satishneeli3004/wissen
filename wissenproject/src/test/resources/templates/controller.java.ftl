package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.jesims.security.common.annotation.Log;
import cn.jesims.security.common.utils.R;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import cn.jesims.security.modules.framework.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
@Api(value = "${table.comment!}接口", tags = "${table.comment!}接口")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
@AllArgsConstructor
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
@AllArgsConstructor
public class ${table.controllerName} {
</#if>

    private final ${table.serviceName} ${table.serviceName?substring(1)?uncap_first};

    /**
     * 列表
     *
     * @return
     */
    @GetMapping
    @ApiOperation(value = "分页列表", notes = "分页列表")
    public R list(${entity} ${entity?uncap_first}) {
        LambdaQueryWrapper<${entity}> wrapper = Wrappers.lambdaQuery(${entity?uncap_first});
        IPage page = ${table.serviceName?substring(1)?uncap_first}.page(getPage(), wrapper);
        return R.ok(page);
    }

    /**
     * 根据id删除数据
     *
     * @param id
     * @return
     */
    @Log("根据ID删除${table.comment!}")
    @DeleteMapping("{id}")
    @ApiOperation(value = "根据id删除数据", notes = "根据id删除数据")
    public R delete(@PathVariable("id") String id) {
        ${table.serviceName?substring(1)?uncap_first}.removeById(id);
        return R.ok();
    }

    /**
     * 新增
     *
     * @param ${entity?uncap_first}
     * @return
     */
    @Log("新增${table.comment!}")
    @PostMapping
    @ApiOperation(value = "新增", notes = "新增")
    public R saveOrUpdate(@Validated @RequestBody ${entity} ${entity?uncap_first}) {
        ${table.serviceName?substring(1)?uncap_first}.save(${entity?uncap_first});
        return R.ok();
    }

    /**
     * 新增
     *
     * @param id 要修改的ID
     * @param ${entity?uncap_first} 数据
     * @return
     */
    @Log("修改${table.comment!}")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改", notes = "修改")
    public R update(@PathVariable("id") String id, @Validated @RequestBody ${entity} ${entity?uncap_first}) {
        ${entity?uncap_first}.setId(id);
        ${table.serviceName?substring(1)?uncap_first}.updateById(${entity?uncap_first});
        return R.ok();
    }

}
</#if>
