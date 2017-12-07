package com.kingthy.command;


/**
 * 描述：命令执行结果
 * @author  likejie
 * @date 2017/10/10.
 */
public class CommandResult {

    private int stauts;
    private String output;

    public int getStauts() {
        return stauts;
    }

    public void setStauts(int stauts) {
        this.stauts = stauts;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
    public static  CommandResult success(String data)
    {
        CommandResult result = new CommandResult();
        result.setStauts(CommandStatus.SUCCESS.getValue());
        result.setOutput(data);
        return result;
    }
    public static CommandResult error(String message)
    {
        CommandResult result = new CommandResult();
        result.setStauts(CommandStatus.ERROR.getValue());
        result.setOutput(message);
        return result;
    }
    public static CommandResult stop()
    {
        CommandResult result = new CommandResult();
        result.setStauts(CommandStatus.STOP.getValue());
        return result;
    }
}
