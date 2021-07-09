package mavenproject.modules.sys.service.impl;

import mavenproject.modules.sys.mapper.SysLogMapper;
import mavenproject.modules.sys.model.entity.SysLog;
import mavenproject.modules.sys.service.SysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author jinghong
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

}
