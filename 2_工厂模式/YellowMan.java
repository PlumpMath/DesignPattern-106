package org.b3log.zephyr;

/**
 * Created by Zephyr on 2017-01-07.
 */
public class YellowMan implements Human {
    @Override
    public void getColor() {
        System.out.println("我是黄种人");
    }

    @Override
    public void talk() {
        System.out.println("我说汉语我自豪");
    }
}
