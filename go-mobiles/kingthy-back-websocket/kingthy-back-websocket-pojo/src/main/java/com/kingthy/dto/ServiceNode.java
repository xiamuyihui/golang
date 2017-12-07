package com.kingthy.dto;

import lombok.Data;
import lombok.ToString;

/**
 *  zookeeper 注册节点对象
 * @author  likejie  2017/11/1.
 */
@Data
@ToString
public class ServiceNode {

    /**
     * 节点路径
     */
    private String path;

    /**
     * 主机
     */
    private String host;

    /**
     * 端口号
     */
    private int port;
    /**
     * 节点名称
     */
    private transient String name;
    /**
     * 节点id
     */
    private transient String nodeId;
    /**
     *  是否永久节点
     */
    private transient boolean persistent;
}
