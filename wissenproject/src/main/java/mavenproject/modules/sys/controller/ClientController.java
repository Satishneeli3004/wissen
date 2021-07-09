package mavenproject.modules.sys.controller;

import cn.hutool.core.util.StrUtil;
import mavenproject.common.annotation.Log;
import mavenproject.common.utils.R;
import mavenproject.modules.framework.controller.BaseController;
import mavenproject.modules.sys.model.entity.SysOauthClientDetails;
import mavenproject.modules.sys.service.SysOauthClientDetailsService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 客户端管理模块
 * </p>
 *
 * @author jinghong
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@Api(value = "客户端管理服务", tags = {"客户端管理服务"})
public class ClientController extends BaseController {
    private final SysOauthClientDetailsService clientDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysOauthClientDetails
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('client:view')")
    @ApiOperation(value = "通过ID查询", notes = "通过ID查询")
    public R getById(@PathVariable Integer id) {
        return R.ok(clientDetailsService.getById(id));
    }


    /**
     * 简单分页查询
     *
     * @param page                  分页对象
     * @param sysOauthClientDetails 系统终端
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAuthority('client:view')")
    @ApiOperation(value = "分页列表", notes = "分页列表")
    public R getOauthClientDetailsPage(Page page, SysOauthClientDetails sysOauthClientDetails) {
        return R.ok(clientDetailsService.page(page, Wrappers.query(sysOauthClientDetails).select(tableFieldInfo -> !tableFieldInfo.getProperty().equals("clientSecret"))));
    }

    /**
     * 添加终端
     *
     * @param sysOauthClientDetails 实体
     * @return success/false
     */
    @PostMapping
    @Log("添加客户端")
    @PreAuthorize("hasAuthority('client:add')")
    @ApiOperation(value = "添加客户端", notes = "添加客户端")
    public R add(@Valid @RequestBody SysOauthClientDetails sysOauthClientDetails) {
        if (StrUtil.isBlank(sysOauthClientDetails.getClientSecret())) {
            return R.failed("请填写秘钥");
        }
        String encode = passwordEncoder.encode(sysOauthClientDetails.getClientSecret());
        sysOauthClientDetails.setClientSecret(encode);
        return R.ok(clientDetailsService.save(sysOauthClientDetails));
    }

    /**
     * 删除终端
     *
     * @param id ID
     * @return success/false
     */
    @Log("删除客户端")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('client:del')")
    @ApiOperation(value = "删除客户端", notes = "删除客户端")
    public R removeById(@PathVariable String id) {
        return R.ok(clientDetailsService.removeClientDetailsById(id));
    }

    /**
     * 编辑终端
     *
     * @param sysOauthClientDetails 实体
     * @return success/false
     */
    @Log("编辑客户端")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('client:edit')")
    @ApiOperation(value = "修改客户端", notes = "修改客户端")
    public R update(@Valid @RequestBody SysOauthClientDetails sysOauthClientDetails, @PathVariable String id) {
        sysOauthClientDetails.setClientId(id);
        if (StrUtil.isNotBlank(sysOauthClientDetails.getClientSecret())) {
            String encode = passwordEncoder.encode(sysOauthClientDetails.getClientSecret());
            sysOauthClientDetails.setClientSecret(encode);
        } else {
            sysOauthClientDetails.setClientSecret(null);
        }
        return R.ok(clientDetailsService.updateClientDetailsById(sysOauthClientDetails));
    }
}
