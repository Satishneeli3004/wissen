package mavenproject.modules.sys.model.entity;

import mavenproject.modules.framework.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author jinghongjiang
 * @since 2019-10-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysDict extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 数据值
     */
    private String value;

    /**
     * 标签名
     */
    private String label;

    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序（升序）
     */
    private Long sort;

    /**
     * 父级编号
     */
    private String parentId;

    /**
     * 备注信息
     */
    private String remarks;


}
