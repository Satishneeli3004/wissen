package mavenproject.modules.framework.enums;

import mavenproject.common.exception.UnknownEnumException;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuTypeEnum implements IEnum {
    MENU(0, "菜单"), BUTTON(1, "按钮");

    @EnumValue
    private final Integer value;

    private final String desc;

    @JsonCreator
    public static MenuTypeEnum getEnum(Integer value) {
        if (value == null) {
            return null;
        }
        for (MenuTypeEnum enums : values()) {
            if (enums.getValue().equals(value)) {
                return enums;
            }
        }
        throw new UnknownEnumException("Error: Invalid value: " + value);
    }

    @Override
    @JsonValue
    public Integer getValue() {
        return this.value;
    }
}
