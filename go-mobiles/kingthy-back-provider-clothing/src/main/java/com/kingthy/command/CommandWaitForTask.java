package com.kingthy.command;

import java.util.concurrent.Callable;


/**
 * 描述：定义一个线程，只用于执行脚本
 * @author  likejie
 * @date 2017/9/5.
 */
public class CommandWaitForTask implements Callable<Integer> {

    private Process process;

    public CommandWaitForTask(Process process) {
        this.process = process;
    }
    @Override
    public Integer call() throws Exception {
        return process.waitFor();
    }

}

