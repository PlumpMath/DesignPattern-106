/**
 * Created by Zephyr on 2017/1/4.
 */
public class Calc {
    private Strategy strategy;
    public void setStrategy(Strategy strategy){
        this.strategy=strategy;
    }
    public double calc(double paramA,double paramB){
        if(this.strategy==null){
            throw new IllegalStateException("你还没有设置计算的策略.");
        }
        return this.strategy.calc(paramA,paramB);
    }
}
