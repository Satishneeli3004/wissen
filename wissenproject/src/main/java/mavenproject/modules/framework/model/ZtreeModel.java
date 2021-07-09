package mavenproject.modules.framework.model;

import lombok.Data;

@Data
public class ZtreeModel extends BaseEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 是否选中
     */
    private Boolean checked;

    /**
     * 父ID
     */
    private String pid;

    /**
     * 是否展开
     */
    private Boolean open;
}
