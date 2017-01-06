package org.b3log.zephyr.ticket;
import org.b3log.zephyr.travel.Path;

/**
 * Created by Zephyr on 2017/1/6.
 */
public interface TicketCenter {
    public Ticket getTicket(Path path);
}
