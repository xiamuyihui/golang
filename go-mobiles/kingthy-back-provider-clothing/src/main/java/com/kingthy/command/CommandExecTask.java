package com.kingthy.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * 描述：定义一个线程，用于执行脚本，并读取脚本的输出流，错误流
 * @author  likejie
 * @date 2017/10/10.
 */
public class CommandExecTask implements Callable<CommandResult> {

    private static final Logger logger = LoggerFactory.getLogger(CommandExecTask.class);

    private Process process = null;
    /**
     * 命令执行结果：0成功，1停止,2异常
     **/
    private CommandResult commandResult = CommandResult.error(null);

    public CommandExecTask(Process pro){
        this.process=pro;
    }
    @Override
    public CommandResult call() throws Exception {
        try {
            /**读取脚本的错误流**/
            String stderror=readStream(process.getErrorStream());
            /**读取脚本的输出流**/
            String stdinput=readStream(process.getInputStream());
            int res=process.waitFor();
            if(res==0){
                if(StringUtils.hasText(stderror)){
                    commandResult= CommandResult.error("脚本内部发生错误："+stderror);
                    logger.error("脚本内部发生错误："+stderror);
                }else{
                    commandResult= CommandResult.success(stdinput);
                    logger.info("脚本执行成功，输出内容："+stdinput);
                }
            }else{
                commandResult=CommandResult.error("脚本执行失败！");
                logger.error("脚本执行失败！");
            }

        }catch (Exception ex){
            logger.error("执行脚本发生异常："+ex.toString());
            commandResult=CommandResult.error("执行脚本发生异常："+ex.toString());
        }
        return  commandResult;
    }
    public  void setStop() {

        logger.info("脚本已停止执行！");
        this.commandResult=CommandResult.stop();
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
