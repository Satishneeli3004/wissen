package mavenproject.modules.sys.service.impl;

import mavenproject.modules.framework.model.ZtreeModel;
import mavenproject.modules.sys.mapper.SysRoleMapper;
import mavenproject.modules.sys.model.entity.SysRole;
import mavenproject.modules.sys.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jinghong 2019/10/17
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<ZtreeModel> authTree(String id) {
        return baseMapper.authTree(id);
    }
}
