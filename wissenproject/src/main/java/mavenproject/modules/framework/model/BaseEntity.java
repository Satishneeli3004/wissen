package mavenproject.modules.framework.model;

import mavenproject.modules.framework.model.convert.Convert;
import lombok.Data;

/**
 * 实体类的基类
 *
 * @author jinghong 2019/10/17
 */
@Data
public class BaseEntity extends Convert {

    protected String id;
}
