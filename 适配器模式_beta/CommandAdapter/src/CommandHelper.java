import org.b3log.zephyr.command.Command;

/**
 * Created by Zephyr on 2017/2/9.
 */
public class CommandHelper {
    private CommandAdapter commandAdapter=new CommandAdapter();

    public void doRequestProcess(String commands){
        String[] commandList = commands.split(",");
        if(null!=commandList && commandList.length>0){
            for(String command:commandList){
                System.out.println(commandAdapter.bind(Command.get(command)));
            }
        }else{
            System.out.println("没听清");
        }
    }
}
