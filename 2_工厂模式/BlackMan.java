package org.b3log.zephyr;

/**
 * Created by Zephyr on 2017-01-07.
 */
public class BlackMan implements Human{
    @Override
    public void getColor() {
        System.out.println("I'm black");
    }

    @Override
    public void talk() {
        System.out.println("I can speak English");
    }
}
