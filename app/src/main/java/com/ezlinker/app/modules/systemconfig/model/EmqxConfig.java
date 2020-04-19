package com.ezlinker.app.modules.systemconfig.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ezlinker.app.common.model.XEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * EMQX配置表
 * </p>
 *
 * @author wangwenhai
 * @since 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("ez_emqx_config")
public class EmqxConfig extends XEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 在线 1
     * 离线 0
     */
    public static final int OFFLINE = 0;
    public static final int ONLINE = 1;

    /**
     * EMQX IP地址
     */
    @NotEmpty(message = "IP地址不可为空值")
    private String ip;

    /**
     * HTTP接口的ID
     */
    @NotEmpty(message = "appId不可为空值")

    private String appId;

    /**
     * APP接口密钥
     */
    @NotEmpty(message = "secret不可为空值")

    private String secret;

    /**
     * 描述
     */
    private String description;

    /**
     * 集群节点名称
     */
    @NotEmpty(message = "节点名称不可为空值,格式为:nodeName@ip")

    private String nodeName;
    /**
     *
     */
    @NotNull(message = "API端口不可为空值")

    private Integer port;

    /**
     * 状态
     */
    private Integer state = 0;
    /**
     * 历史运行状态
     */
    @TableField(exist = false)
    private Object historyRunningState;
    /**
     * 当前状态
     */
    @TableField(exist = false)
    private Object currentRunningState;

}
