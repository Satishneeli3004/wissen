package mavenproject.modules.quartz.service.impl;

import mavenproject.modules.quartz.entity.SysJob;
import mavenproject.modules.quartz.mapper.SysJobMapper;
import mavenproject.modules.quartz.service.SysJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 定时任务调度表
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {

}
