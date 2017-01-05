/**
 * Created by Zephyr on 2017/1/5.
 */
public class InvokerRole {
    private Command command1;
    private Command command2;
    public void setCommand1(Command command1){
        this.command1=command1;
    }
    public void setCommand2(Command command2){
        this.command2=command2;
    }
    public void invoke(int args){
        if(args==0){
            command1.execute();
            command2.execute();
        }else if (args==1){
            command1.undo();
            command2.undo();
        }
    }
}
