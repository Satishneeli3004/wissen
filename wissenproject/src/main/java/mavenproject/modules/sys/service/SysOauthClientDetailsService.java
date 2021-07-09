package mavenproject.modules.sys.service;

import mavenproject.modules.sys.model.entity.SysOauthClientDetails;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 客户端
 *
 * @author jinghong
 */
public interface SysOauthClientDetailsService extends IService<SysOauthClientDetails> {
    /**
     * 通过ID删除客户端
     *
     * @param id
     * @return
     */
    Boolean removeClientDetailsById(String id);

    /**
     * 根据客户端信息
     *
     * @param sysOauthClientDetails
     * @return
     */
    Boolean updateClientDetailsById(SysOauthClientDetails sysOauthClientDetails);
}