package org.b3log.zephyr;

/**
 * Created by Zephyr on 2017-01-07.
 */
public class WhiteMan implements Human {
    @Override
    public void getColor() {
        System.out.println("I'm white");
    }

    @Override
    public void talk() {
        System.out.println("I speak American English");
    }
}
