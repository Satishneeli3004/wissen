package mavenproject.modules.sys.service.impl;

import mavenproject.common.constant.CacheConstants;
import mavenproject.modules.framework.model.ComboBoxEntity;
import mavenproject.modules.sys.mapper.SysDictMapper;
import mavenproject.modules.sys.model.entity.SysDict;
import mavenproject.modules.sys.service.SysDictService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.NonNull;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jinghong 2019/10/17
 */
@Service
@CacheConfig(cacheNames = CacheConstants.DICT_KEY)
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Override
    @CacheEvict(allEntries = true)
    public boolean updateById(SysDict entity) {
        return super.updateById(entity);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean save(SysDict entity) {
        return super.save(entity);
    }

    @Override
    @CacheEvict(allEntries = true)
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    @Cacheable(key = "'type:' + #type")
    public List<ComboBoxEntity<String, String>> type(@NonNull String type) {
        List<SysDict> list = list(Wrappers.<SysDict>lambdaQuery()
                .select(SysDict::getType, SysDict::getValue, SysDict::getLabel)
                .eq(SysDict::getType, type));
        List<ComboBoxEntity<String, String>> collect = list.stream()
                .map(sysDict -> new ComboBoxEntity<>(sysDict.getValue(), sysDict.getLabel()))
                .collect(Collectors.toList());
        return collect;
    }
}
