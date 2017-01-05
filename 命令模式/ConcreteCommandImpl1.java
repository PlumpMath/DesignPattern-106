/**
 * Created by Zephyr on 2017/1/5.
 */
public class ConcreteCommandImpl1 implements Command {
    private ReceiverRole receiverRole;

    public ConcreteCommandImpl1(ReceiverRole receiverRole){
        this.receiverRole=receiverRole;
    }

    @Override
    public void execute() {
        receiverRole.opActionUpdateAge(1001);
    }

    @Override
    public void undo() {
        receiverRole.rollBackAge();
    }

    @Override
    public void redo() {

    }
}
