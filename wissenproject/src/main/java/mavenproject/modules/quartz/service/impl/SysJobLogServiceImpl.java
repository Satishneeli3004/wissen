package mavenproject.modules.quartz.service.impl;

import mavenproject.modules.quartz.entity.SysJobLog;
import mavenproject.modules.quartz.mapper.SysJobLogMapper;
import mavenproject.modules.quartz.service.SysJobLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 定时任务执行日志表
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements SysJobLogService {

}
