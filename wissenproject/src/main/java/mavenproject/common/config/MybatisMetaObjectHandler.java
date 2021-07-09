package mavenproject.common.config;

import mavenproject.common.security.util.SecurityUtils;
import mavenproject.modules.framework.enums.StatusEnum;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Mybatis Plus自动填充配置
 *
 * @author jinghong
 */
@Slf4j
@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入自动填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        // 创建时间
        this.setValueByNotNull("createTime", now, metaObject);
        // 修改时间
        this.setValueByNotNull("updateTime", now, metaObject);
        // 删除标记 1: 启用 0: 删除
        this.setValueByNotNull("delFlag", StatusEnum.ENABLE, metaObject);
        try {
            String userId = SecurityUtils.getUser().getId();
            this.setValueByNotNull("createBy", userId, metaObject);
            this.setValueByNotNull("updateBy", userId, metaObject);
        } catch (Exception e) {
            // log.warn("自动填充创建人失败 {}", e.getMessage());
        }
    }

    /**
     * 扩展不为null才填充
     *
     * @param fieldName
     * @param fieldVal
     * @param metaObject
     */
    private void setValueByNotNull(String fieldName, Object fieldVal, MetaObject metaObject) {
        if (metaObject.hasSetter(fieldName)) {
            // 为空进行填充
            Object fieldValByName = getFieldValByName(fieldName, metaObject);
            if (Objects.isNull(fieldValByName)) {
                this.setFieldValByName(fieldName, fieldVal, metaObject);
            }
        }
    }

    /**
     * 修改自动填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setValueByNotNull("updateTime", LocalDateTime.now(), metaObject);
        try {
            this.setValueByNotNull("updateBy", SecurityUtils.getUser().getId(), metaObject);
        } catch (Exception e) {
            // log.warn("自动填充修改人失败 {}", e.getMessage());
        }
    }
}