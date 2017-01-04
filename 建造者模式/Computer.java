/**
 * Created by Zephyr on 2017/1/4.
 */
public abstract class Computer {
    protected int iCpuCore=1;
    protected int iRamSize=0;
    protected String strOs="Dos";
    //构造函数设置为protected是什么效果
    protected Computer(){
    }
    public abstract void setCPU(int core);
    public abstract void setRAM(int gb);
    public abstract void setOs(String os);
    @Override
    public String toString(){
        return "Computer [CpuCore="+iCpuCore+",RamSize="+iRamSize+",Os="+strOs+"]";
    }
}
