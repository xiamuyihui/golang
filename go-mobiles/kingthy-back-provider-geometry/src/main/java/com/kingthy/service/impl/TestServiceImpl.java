package com.kingthy.service.impl;

import com.kingthy.command.CommandStreamThread;
import com.kingthy.command.CommandWaitForThread;
import com.kingthy.conf.CommandSetting;
import com.kingthy.service.ITest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by likejie on 2017/9/5.
 */
@Service
public class TestServiceImpl implements ITest {

    @Autowired
    private  CommandSetting commandSetting;

    @Override
    public String testCommand(String content) {

        InputStreamReader stdISR = null;//标准输出
        InputStreamReader errISR = null;//错误输出
        long timeout = 10 * 1000;
        Process process = null;
        // 程序路径
        String command = commandSetting.getCommand();
        try {
            process = Runtime.getRuntime().exec(command);

            CommandStreamThread errorGobbler = new CommandStreamThread(process.getErrorStream());
            CommandStreamThread outputGobbler = new CommandStreamThread(process.getInputStream());

            errorGobbler.start();
            // 必须先等待错误输出ready再建立标准输出
            while (!errorGobbler.isReady()) {
                Thread.sleep(10);
            }
            outputGobbler.start();
            CommandWaitForThread commandThread = new CommandWaitForThread(process);
            commandThread.start();

            long commandTime = new Date().getTime();
            long nowTime = new Date().getTime();
            boolean timeoutFlag = false;
            while (!commandThread.isFinish()) {
                if (nowTime - commandTime > timeout) {
                    timeoutFlag = true;
                    break;
                } else {
                    Thread.sleep(1000);
                    nowTime = new Date().getTime();
                }
            }
            if (timeoutFlag) {
                // 命令超时
                errorGobbler.setTimeout(1);
                outputGobbler.setTimeout(1);
                System.out.println("正式执行命令：" + command + "超时");
            }

            while (true) {
                if (errorGobbler.isReadFinish() && outputGobbler.isReadFinish()) {
                    break;
                }
                Thread.sleep(10);
            }
            return outputGobbler.getOutputContent();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return null;
    }
}
