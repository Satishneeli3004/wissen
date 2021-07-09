package mavenproject.modules.framework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * layui FormSelects
 *
 * @author jinghong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormSelects {

    private String name;
    private String value;
    private String selected;
    private String disabled;
}
