/**
 * Created by Zephyr on 2017/1/4.
 */
public abstract class Builder {
    public abstract void buildCPU(int core);
    public abstract void buildRAM(int gb);
    public abstract void buildOS(String os);
    public abstract Computer create();
}
