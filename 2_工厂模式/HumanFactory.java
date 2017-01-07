package org.b3log.zephyr;

/**
 * Created by Zephyr on 2017-01-07.
 */
public class HumanFactory extends AbstractHumanFactory {
    /*
     * 也可以做简化，去掉抽象类，将createHuman作为静态方法调用
     * 也可与单例模式结合，实现单例工厂
     */
    @Override
    public <T extends Human> T createHuman(Class<T> c) {
        Human human=null;
        try{
            human = (Human)Class.forName(c.getName()).newInstance();
        }catch(Exception e){
            System.out.println("人种生成错误");
        }
        return (T)human;
    }
}
