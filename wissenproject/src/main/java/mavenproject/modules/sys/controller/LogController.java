package mavenproject.modules.sys.controller;

import mavenproject.common.utils.R;
import mavenproject.modules.framework.controller.BaseController;
import mavenproject.modules.sys.model.entity.SysLog;
import mavenproject.modules.sys.service.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统日志
 *
 * @author jinghong
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/log")
@Api(value = "系统日志", tags = "系统日志")
public class LogController extends BaseController {

    private final SysLogService logService;

    /**
     * 列表
     *
     * @param log
     * @return
     */
    @RequestMapping
    @PreAuthorize("hasAuthority('sys:log:view')")
    @ApiOperation(value = "分页列表", notes = "分页列表")
    public R list(SysLog log) {
        LambdaQueryWrapper<SysLog> wrapper = Wrappers.lambdaQuery(log)
                .orderByDesc(SysLog::getCreateTime);

        IPage page = logService.page(getPage(), wrapper);
        return R.ok(page);
    }

}