/**
 * Created by Zephyr on 2017/1/6.
 */
public class V220Adapter implements IAdapter {
    private ChangeAdapter changeAdapter;
    public V220Adapter(){
        changeAdapter=new ChangeAdapter();
    }
    @Override
    public String Drive() {
        return changeAdapter.Web("(2) 输出电压：220V");
    }
}
