/**
 * Created by Zephyr on 2017/1/5.
 */
public class ClientRole {
    public void assembleAction(){
        ReceiverRole receiverRole=new ReceiverRole();
        Command command1=new ConcreteCommandImpl1(receiverRole);
        Command command2=new ConcreteCommandImpl2(receiverRole);
        InvokerRole invokerRole=new InvokerRole();
        invokerRole.setCommand1(command1);
        invokerRole.setCommand2(command2);
        invokerRole.invoke(0);
        invokerRole.invoke(1);
    }
}
