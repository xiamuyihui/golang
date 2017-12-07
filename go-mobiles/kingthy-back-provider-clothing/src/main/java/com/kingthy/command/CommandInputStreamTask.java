package com.kingthy.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * 描述：定义一个线程，只用于读取脚本的输出流，错误流
 * @author  likejie
 * @date 2017/10/10.
 */
public class CommandInputStreamTask implements Callable<CommandResult> {
    private static final Logger logger = LoggerFactory.getLogger(CommandInputStreamTask.class);

    private InputStream inputStream;

    /**
     * 命令执行结果：0成功，1停止,2异常
     **/
    private CommandResult commandResult = CommandResult.success(null);

    private boolean ready = false;

    public CommandInputStreamTask(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public CommandResult call() throws Exception {

        try {
            String content = readStream(inputStream);
            commandResult=CommandResult.success(content);
        } catch (Exception ioe) {
            commandResult = CommandResult.error(ioe.toString());
        }
        return commandResult;
    }

    public synchronized boolean isReady() {
        return ready;
    }

    public synchronized void setStop() {
        this.commandResult = CommandResult.stop();
    }
    /**
     *
     * 读取流
     */
    public String readStream(InputStream inputStream) throws Exception{
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuffer sbuffer = new StringBuffer();
        try {
            isr = new InputStreamReader(inputStream);
            br = new BufferedReader(isr);
            String line;
            ready=true;
            while (commandResult.getStauts() != CommandStatus.STOP.getValue()) {
                if ((line = br.readLine()) != null) {
                    sbuffer.append(line);
                } else {
                    break;
                }
            }
        } catch (Exception ioe) {
            throw ioe;
        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
        }
        return sbuffer.toString();
    }
}
