package mavenproject.modules.sys.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户菜单信息
 *
 * @author jinghong 2019/10/17
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysUserMenu extends TreeNode {

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 地址
     */
    private String url;

    /**
     * 图标
     */
    private String icon;
}
