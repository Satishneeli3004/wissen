package mavenproject.modules.sys.model.entity;

import mavenproject.modules.framework.model.BaseEntity;
import lombok.Data;

/**
 * (SysUserSocial)实体类
 *
 * @author jinghong
 * @since 2019-11-27 21:07:29
 */
@Data
public class SysUserSocial extends BaseEntity {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 类型
     */
    private String type;

}