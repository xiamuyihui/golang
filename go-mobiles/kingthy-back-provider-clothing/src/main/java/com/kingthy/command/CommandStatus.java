package com.kingthy.command;

/**
 * 描述：命令执行状态
 * @author  likejie
 * @date 2017/10/10.
 */
public enum CommandStatus {

    /**执行成功***/
    SUCCESS(0),
    /**中止执行***/
    STOP(1),
    /**执行有误***/
    ERROR(2);

    private Integer value;

    public Integer getValue()
    {
        return value;
    }
    CommandStatus(Integer value)
    {
        this.value = value;
    }
}
