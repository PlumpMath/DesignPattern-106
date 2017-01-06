/**
 * Created by Zephyr on 2017/1/6.
 */
public class V110Adapter extends ChangeAdapter implements IAdapter {
    @Override
    public String Drive() {
        return this.Web("(1) 输出电压：110V");
    }
}
