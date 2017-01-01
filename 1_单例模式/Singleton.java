package org.b3log.Singleton;

public class Singleton {
    //向外界暴漏一个实例对象以供使用（外部无法自己获取实例对象）
    private static final Singleton singleton=new Singleton();

    private Singleton(){}

    public static Singleton getSingleton(){
        return singleton;
    }
}
/*
 * 线程不安全的单例模式,高并发下会产生多个实例
 */
/*
public class Singleton{
    private static final Singleton singleton=null;
    private Singleton(){}
    public static Singleton getSingleton(){
        if(singleton==null){
            singleton=new Singleton():
        }else{
            return singleton;
        }
    }
 */