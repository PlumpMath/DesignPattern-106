/**
 * Created by Zephyr on 2017/1/5.
 */
public interface Command {
    public void execute();
    public void undo();
    public void redo();
}
