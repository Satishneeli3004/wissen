package mavenproject.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import mavenproject.modules.sys.mapper.SysUserRoleMapper;
import mavenproject.modules.sys.model.entity.SysUserRole;
import mavenproject.modules.sys.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserRole(String uid, List<String> roleId) {
        Assert.notBlank(uid);
        super.remove(Wrappers.<SysUserRole>lambdaUpdate().eq(SysUserRole::getUserId, uid));

        if (CollUtil.isNotEmpty(roleId)) {
            List<SysUserRole> userRoles = roleId.stream().map(rid -> new SysUserRole(uid, rid))
                    .collect(Collectors.toList());
            saveBatch(userRoles);
        }
    }

    @Override
    public Set<String> userRoleIds(String uid) {
        List<SysUserRole> list = list(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getUserId, uid)
                .select(SysUserRole::getRoleId));
        Set<String> roles = list.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
        return roles;
    }
}
