/**
 * Created by Zephyr on 2017/1/4.
 */
public class DivStrategy implements Strategy{
    @Override
    public double calc(double paramA, double paramB) {
        System.out.println("执行除法策略...");
        if(paramB==0){
            throw new IllegalArgumentException("除数不能为0!");
        }
        return paramA/paramB;
    }
}
