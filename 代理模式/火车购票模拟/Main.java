import org.b3log.zephyr.ticket.Ticket;
import org.b3log.zephyr.ticket.TicketCenter;
import org.b3log.zephyr.ticket.TicketCenterImpl;
import org.b3log.zephyr.travel.Path;
import org.b3log.zephyr.travel.Train;

public class Main {
    public static void main(String[] args) {
        TicketCenter ticketCenter=new TicketCenterImpl();
        Path path=new Path("BJ","SH");
        Ticket ticket = ticketCenter.getTicket(path);//获得北京到上海的车票
        Train train=new Train();
        train.travel(ticket);
    }
}
