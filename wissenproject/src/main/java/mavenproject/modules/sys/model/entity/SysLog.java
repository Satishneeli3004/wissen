package mavenproject.modules.sys.model.entity;

import mavenproject.modules.framework.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author jinghong
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_log")
public class SysLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableField(condition = SqlCondition.LIKE)
    private String username;

    /**
     * 用户操作
     */
    @TableField(condition = SqlCondition.LIKE)
    private String operation;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时长(毫秒)
     */
    private Long time;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 操作系统
     */
    @TableField(condition = SqlCondition.LIKE)
    private String osName;

    /**
     * 设备名
     */
    @TableField(condition = SqlCondition.LIKE)
    private String device;

    /**
     * 浏览器类型
     */
    @TableField(condition = SqlCondition.LIKE)
    private String browserType;

    /**
     * 访问地址
     */
    private String url;


}
