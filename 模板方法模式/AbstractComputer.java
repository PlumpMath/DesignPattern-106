/**
 * Created by Zephyr on 2017/1/4.
 */
public abstract class AbstractComputer {
    protected void powerOn(){
        System.out.println("开启电源");
    }
    protected void checkHardware(){
        System.out.println("硬件检查");
    }
    protected void loadOS(){
        System.out.println("载入操作系统");
    }
    protected void login(){
        System.out.println("小白的电脑无验证，直接进入系统");
    }
    public final void startUp(){
        System.out.println("------开机 START------");
        powerOn();
        checkHardware();
        loadOS();
        login();
        System.out.println("------开机 END------");
    }
}
