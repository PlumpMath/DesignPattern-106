package org.b3log.Singleton;

import java.util.ArrayList;
import java.util.List;

//限定实例个数的单例模式扩展
public class Singletons {
    private static int max=5;
    private static List<Singletons> singletonsList=new ArrayList<Singletons>();
    private static int index=0;
    static{
        for(int i=0;i<max;i++){
            singletonsList.add(new Singletons());
        }
    }
    private Singletons(){}
    public static Singletons getInstance(){
        if(index>=max){
            index=0;
        }
        return singletonsList.get(index++);
    }
}