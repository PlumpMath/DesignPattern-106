/**
 * Created by Zephyr on 2017/1/4.
 */
public class SubStrategy implements Strategy {
    @Override
    public double calc(double paramA, double paramB) {
        System.out.println("执行减法策略...");
        return paramA-paramB;
    }
}
