package com.kingthy.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by likejie on 2017/9/5.
 */
public class CommandStreamThread extends Thread {


    private static final  Logger logger= LoggerFactory.getLogger(CommandStreamThread.class);

    private InputStream inputStream;

    /**命令执行结果：0成功，1超时**/
    private int commandResult=0;

    private boolean readFinish = false;

    private boolean ready = false;

    private String outputContent;

    public CommandStreamThread(InputStream is) {
        this.inputStream = is;
    }

    public void run() {
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb=new StringBuffer();
            String line;
            ready = true;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            outputContent=sb.toString();

        } catch (IOException ioe) {
            logger.error(ioe.toString());
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
            } catch (IOException ioe) {
                logger.error(ioe.toString());
            }
            readFinish = true;
        }
    }
    public InputStream getInputStream() {
        return inputStream;
    }
    public boolean isReadFinish() {
        return readFinish;
    }
    public boolean isReady() {
        return ready;
    }
    public void setTimeout(int timeout) {
        this.commandResult = timeout;
    }

    public String getOutputContent() {
        return outputContent;
    }
}
