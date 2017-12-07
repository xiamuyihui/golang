package com.kingthy.command;

import com.kingthy.conf.CommandSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 描述：
 * @author  likejie
 * @date 2017/10/10.
 */
@Component
public class CommandManager {

    private static final Logger logger= LoggerFactory.getLogger(CommandManager.class);
    @Autowired
    private CommandSetting commandSetting;

    public CommandManager(){

    }
    /**
     *
     *单线程处理 single-thread
     * */
    public  CommandResult  stExecCommand( ExecutorService threadpool,
                                        String [] inputParms){
        CommandResult result;
        Process process = null;
        CommandExecTask task=null;
        /**超时时间设置**/
        long timeout=commandSetting.getTimeout();
        try {
            //将命令加入进程中
            process = Runtime.getRuntime().exec(inputParms);
            task=new CommandExecTask(process);
            //开启新线程执行
            Future<CommandResult> futureTask = threadpool.submit(task);
            result = futureTask.get(timeout, TimeUnit.SECONDS);

        }catch (Exception ex){
            if(ex instanceof TimeoutException){
                result = CommandResult.error("脚本" + inputParms[0] + "执行超时");
            }else{
                result = CommandResult.error("脚本" + inputParms[0] + "执行失败:" + ex.toString());
            }
        }finally {
            if(task!=null){
                task.setStop();
            }
            if (process != null) {
                process.destroy();//结束进程
            }
        }
        return result;

    }
    /**
     *
     * 多线程处理 multi-thread
     **/
    public  CommandResult  mtExecCommand(
            ExecutorService threadpool,
            String [] inputParms)
    {
        /**超时时间设置**/
        long timeout=commandSetting.getTimeout();


        CommandResult result;
        Process process = null;
        CommandInputStreamTask readErrorTask = null;
        CommandInputStreamTask readInputTask = null;
        try {
            process = Runtime.getRuntime().exec(inputParms);
            //读取标准错误流
            readErrorTask = new CommandInputStreamTask(process.getErrorStream());
            //读取标准输出流
            readInputTask = new CommandInputStreamTask(process.getInputStream());

            //线程一：输出错误
            Future<CommandResult> futureError = threadpool.submit(readErrorTask);
            // 必须先等待错误输出ready再建立标准输出
            while (!readErrorTask.isReady()) {
                Thread.sleep(10);
            }
            //线程二：输出内容
            Future<CommandResult> futureInput = threadpool.submit(readInputTask);
            while (!readInputTask.isReady()) {
                Thread.sleep(10);
            }
            //线程三：等待脚本执行进程执行结束
            CommandWaitForTask commandWaitForTask = new CommandWaitForTask(process);
            Future<Integer> commandFuture=threadpool.submit(commandWaitForTask);
            Integer res=commandFuture.get(timeout, TimeUnit.SECONDS);

            if(res==0){
                //获取脚本标准错误流
                CommandResult errorResult = futureError.get();
                //获取脚本标准输入流
                CommandResult inputResult = futureInput.get();

                if(errorResult.getStauts() == CommandStatus.ERROR.getValue()){
                    logger.error("错误流读取失败："+errorResult.getOutput());
                    result=CommandResult.error("错误流读取失败："+errorResult.getOutput());
                }else{

                    if (StringUtils.hasText(errorResult.getOutput())) {
                        logger.error("脚本内部执行失败:"+errorResult.getOutput());
                        result=CommandResult.error("脚本内部执行失败:"+errorResult.getOutput());
                    }else{
                        /**获取脚本执行结果**/
                        if(inputResult.getStauts() == CommandStatus.ERROR.getValue()){
                            logger.error("标准输入流读取失败："+inputResult.getOutput());
                            result=CommandResult.error("标准输入流读取失败："+inputResult.getOutput());
                        }else{
                            /**命令执行成功，输出结果**/
                            result=inputResult;
                            logger.info("脚本执行成功，返回结果："+inputResult.getOutput());
                        }
                    }
                }
            }else{
                result=CommandResult.error("脚本执行失败！");
            }
        }catch (Exception ex) {
            logger.error("命令" + inputParms[0] + "执行失败:"+ex.toString());
            result=CommandResult.error("命令" + inputParms[0] + "执行失败:"+ex.toString());
        } finally {
            /**task中有阻塞时，无法通过futureOutput.cancel方法来终止线程，需要手动停止****/
            readErrorTask.setStop();//停止任务，保证资源释放，线程回收
            readInputTask.setStop();//停止任务，保证资源释放，线程回收
            if (process != null) {
                process.destroy();//结束进程
            }
        }
        return result;
    }
}
