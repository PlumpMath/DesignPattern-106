package org.b3log.zephyr.ticket;
/**
 * Created by Zephyr on 2017/1/6.
 */
public interface TicketSeller {
    public Ticket buyTicket(String end);
    public boolean beforeBuyTicket();
    public Ticket checkIn(Ticket ticket);
}
