public class Main {
    public static void main(String[] args) {
        double paramA=5;
        double paramB=21;
        System.out.println(calc(new AddStrategy(),paramA,paramB));
        System.out.println(calc(new SubStrategy(),paramA,paramB));
        System.out.println(calc(new MultiStrategy(),paramA,paramB));
        System.out.println(calc(new DivStrategy(),paramA,paramB));
    }
    public static double calc(Strategy strategy,double paramA,double paramB){
        Calc calc=new Calc();
        calc.setStrategy(strategy);
        return calc.calc(paramA,paramB);
    }
}
