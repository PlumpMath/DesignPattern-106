/**
 * Created by Zephyr on 2017/1/4.
 */
public class AppleComputer extends Computer{

    protected AppleComputer(){
    }

    @Override
    public void setCPU(int core) {
        iCpuCore=core;
    }

    @Override
    public void setRAM(int gb) {
        iRamSize=gb;
    }

    @Override
    public void setOs(String os) {
        strOs=os;
    }
}
