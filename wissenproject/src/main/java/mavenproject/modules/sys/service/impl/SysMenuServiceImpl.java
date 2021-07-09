package mavenproject.modules.sys.service.impl;

import mavenproject.modules.sys.mapper.SysMenuMapper;
import mavenproject.modules.sys.model.entity.SysMenu;
import mavenproject.modules.sys.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author jinghong 2019/10/17
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
}
