public class Main {
    public static void main(String[] args) {
        Builder builder=new ApplePCBuilder();
        Director director=new Director(builder);
        director.construct(4,2,"Mac OS X 10.9.1");
        System.out.println("Computer Info:"+builder.create().toString());
    }
}
