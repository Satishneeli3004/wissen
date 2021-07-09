package mavenproject.modules.sys.model.entity;

import mavenproject.modules.framework.enums.MenuTypeEnum;
import mavenproject.modules.framework.model.BaseModel;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 菜单
 *
 * @author jinghong 2019/10/17
 */
@Data
public class SysMenu extends BaseModel {

    /**
     * 菜单名称
     */
    @TableField(condition = SqlCondition.LIKE)
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 父级ID
     */
    private String pid;

    /**
     * 类型
     */
    @NotNull(message = "菜单类型不能为空")
    private MenuTypeEnum type;

    /**
     * 地址
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序(越小越靠前)
     */
    private Long sort;

    /**
     * 权限
     */
    private String authority;
}
