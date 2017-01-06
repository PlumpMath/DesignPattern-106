public class Main {
    public static void main(String[] args) {
        IAdapter dap=new Adapter();
        System.out.println(dap.Drive());
        dap=new V110Adapter();
        System.out.println(dap.Drive());
        dap=new V220Adapter();
        System.out.println(dap.Drive());
    }
}
