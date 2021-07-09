package mavenproject.modules.framework.model.convert;

import cn.hutool.core.bean.BeanUtil;

import java.io.Serializable;

/**
 * <p>
 * 普通实体父类
 * </p>
 *
 * @author jinghong
 */
public class Convert implements Serializable {

    /**
     * 获取自动转换后的JavaBean对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T convert(Class<T> clazz) {
        return BeanUtil.toBean(this, clazz);
    }
}
