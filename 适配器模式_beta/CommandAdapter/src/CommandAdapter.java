import org.b3log.zephyr.command.BaseCommander;
import org.b3log.zephyr.command.Command;
import org.b3log.zephyr.command.CookDinnerCommanderImpl;
import org.b3log.zephyr.command.MakeBedCommanderImpl;
import org.b3log.zephyr.command.WashClothesCommanderImpl;
import org.b3log.zephyr.command.WashDishesCommanderImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zephyr on 2017/2/9.
 */
public class CommandAdapter {
    private Map<String, BaseCommander> commandName4Commander;

    public CommandAdapter(){
        commandName4Commander=new HashMap<String,BaseCommander>();
        commandName4Commander.put(Command.COOK_DINNER.value,new CookDinnerCommanderImpl());
        commandName4Commander.put(Command.MAKE_BED.value,new MakeBedCommanderImpl());
        commandName4Commander.put(Command.WASH_CLOTHES.value,new WashClothesCommanderImpl());
        commandName4Commander.put(Command.WASH_DISHES.value,new WashDishesCommanderImpl());
    }

    public String bind(Command command) {
        BaseCommander commander = this.commandName4Commander.get(command.value);
        return commander.execute(command.toString());
    }
}
