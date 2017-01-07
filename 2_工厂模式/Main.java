package org.b3log.zephyr;

public class Main {

    public static void main(String[] args) {
        AbstractHumanFactory YinYangLu=new HumanFactory();
        Human white=YinYangLu.createHuman(WhiteMan.class);
        white.getColor();
        white.talk();
        Human black=YinYangLu.createHuman(BlackMan.class);
        black.getColor();
        black.talk();
        Human yellow=new YellowHumanFactory().createHuman();
        yellow.getColor();
        yellow.talk();
    }
}
