package org.b3log.zephyr.command;

/**
 * Created by Zephyr on 2017/2/9.
 */
public class CookDinnerCommanderImpl extends BaseCommander {
    @Override
    public String execute(String para) {
        return para+": 做完了";
    }
}
