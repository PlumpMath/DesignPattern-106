/**
 * Created by Zephyr on 2017/1/4.
 */
public class ApplePCBuilder extends Builder{
    private Computer oApplePc=new AppleComputer();

    @Override
    public void buildCPU(int core) {
        oApplePc.setCPU(core);
    }

    @Override
    public void buildRAM(int gb) {
        oApplePc.setRAM(gb);
    }

    @Override
    public void buildOS(String os) {
        oApplePc.setOs(os);
    }

    @Override
    public Computer create() {
        //为什么不在此处调用三个build，而要手工在外部调用？为了扩展？
        return oApplePc;
    }
}
