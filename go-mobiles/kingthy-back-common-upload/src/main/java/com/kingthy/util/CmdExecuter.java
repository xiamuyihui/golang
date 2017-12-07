package com.kingthy.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 描述：CmdExecuter
 * @author  赵生辉
 * @date 2017/10/23.
 */
public class CmdExecuter
{
    /**
     * 执行指令
     * @param cmd 执行指令
     * @param getter 指令返回处理接口，若为null则不处理输出
     */
    static public void exec( List<String> cmd, IStringGetter getter ){
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(cmd);
            builder.redirectErrorStream(true);
            Process proc = builder.start();
            BufferedReader stdout = new BufferedReader(
                new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = stdout.readLine()) != null) {
                if( getter != null ) {
                    getter.dealString(line);
                }
            }
            proc.waitFor();
            stdout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public interface IStringGetter {
        /**
         * 处理字符串
         * @param str
         */
        public void dealString( String str );
    }
}
