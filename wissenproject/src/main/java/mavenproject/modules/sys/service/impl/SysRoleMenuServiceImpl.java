package mavenproject.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import mavenproject.modules.sys.mapper.SysRoleMenuMapper;
import mavenproject.modules.sys.model.entity.SysRoleMenu;
import mavenproject.modules.sys.model.parm.SysRoleMenuParm;
import mavenproject.modules.sys.service.SysRoleMenuService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jinghong
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleMenu(@NonNull SysRoleMenuParm parm) {
        Assert.notBlank(parm.getId(), "角色ID不能为空");
        baseMapper.delete(Wrappers.<SysRoleMenu>lambdaUpdate().eq(SysRoleMenu::getRoleId, parm.getId()));

        List<String> menuIds = parm.getMenuIds();
        if (CollUtil.isNotEmpty(menuIds)) {
            List list = menuIds.stream().map(menuId -> new SysRoleMenu(parm.getId(), menuId)).collect(Collectors.toList());
            saveBatch(list);
        }
    }
}
