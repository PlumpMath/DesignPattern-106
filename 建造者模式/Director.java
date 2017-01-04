/**
 * Created by Zephyr on 2017/1/4.
 */
public class Director {
    Builder builder=null;
    public Director(Builder builder){
        this.builder=builder;
    }
    public void construct(int cpu,int ram,String os){
        this.builder.buildCPU(cpu);
        this.builder.buildRAM(ram);
        this.builder.buildOS(os);
    }
}
