package mavenproject.modules.sys.service.impl;

import mavenproject.common.constant.CacheConstants;
import mavenproject.modules.sys.mapper.SysOauthClientDetailsMapper;
import mavenproject.modules.sys.model.entity.SysOauthClientDetails;
import mavenproject.modules.sys.service.SysOauthClientDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jinghong
 */
@Service
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetails> implements SysOauthClientDetailsService {

    /**
     * 通过ID删除客户端
     *
     * @param id
     * @return
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#id")
    public Boolean removeClientDetailsById(String id) {
        return this.removeById(id);
    }

    /**
     * 根据客户端信息
     *
     * @param clientDetails
     * @return
     */
    @Override
    @CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientDetails.clientId")
    public Boolean updateClientDetailsById(SysOauthClientDetails clientDetails) {
        return this.updateById(clientDetails);
    }
}
