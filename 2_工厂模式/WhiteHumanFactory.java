package org.b3log.zephyr;

/**
 * Created by Zephyr on 2017-01-07.
 */
public class WhiteHumanFactory extends AbstractHumenFactory {

    @Override
    public Human createHuman() {
        return new WhiteMan();
    }
}
