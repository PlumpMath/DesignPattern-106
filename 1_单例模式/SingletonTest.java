package org.b3log.Singleton;

public class SingletonTest {

    public static void main(String[] args) {
	// write your code here
        Singleton s1=Singleton.getSingleton();
        Singleton s2=Singleton.getSingleton();
        if(s1==s2){
            System.out.println("s1 = s2");
        }else{
            System.out.println("s1 != s2");
        }
    }
}