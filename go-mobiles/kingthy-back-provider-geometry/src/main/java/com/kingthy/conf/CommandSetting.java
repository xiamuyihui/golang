package com.kingthy.conf;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by likejie on 2017/9/5.
 */
@Component
public class CommandSetting {

    @Value("${command.test.path}")
    private  String command;


    public String getCommand() {
        return command;
    }
}
