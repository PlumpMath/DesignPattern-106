/**
 * Created by Zephyr on 2017/1/5.
 */
public class ConcreteCommandImpl2 implements Command {
    private ReceiverRole receiverRole;

    public ConcreteCommandImpl2(ReceiverRole receiverRole){
        this.receiverRole=receiverRole;
    }

    @Override
    public void execute() {
        receiverRole.opActionUpdateName("lijunhuayc");
    }

    @Override
    public void undo() {
        receiverRole.rollBackName();
    }

    @Override
    public void redo() {

    }
}
