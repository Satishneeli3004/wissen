package mavenproject.modules.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * <p>
 * 状态枚举
 * </p>
 *
 * @author jinghong
 */
@Getter
public enum StatusEnum {

    DISABLE(0), ENABLE(1);

    @EnumValue
    private final Integer value;

    StatusEnum(final Integer value) {
        this.value = value;
    }
}
