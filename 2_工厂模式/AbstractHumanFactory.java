package org.b3log.zephyr;

/**
 * Created by Zephyr on 2017-01-07.
 */
public abstract class AbstractHumanFactory {
    public abstract <T extends Human> T createHuman(Class<T> c);
}
