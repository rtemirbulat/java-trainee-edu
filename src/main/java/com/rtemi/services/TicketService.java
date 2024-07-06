package com.rtemi.services;

import com.rtemi.interfaces.Printable;
import com.rtemi.model.ConcertTicket;
import com.rtemi.model.TicketUID;
import com.rtemi.model.entity.Admin;
import com.rtemi.model.entity.User;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class TicketService extends TicketUID implements Printable {
    private static List<ConcertTicket> tickets;

    public static void main(String[] args) {

        BigDecimal weight = new BigDecimal(4.200);
        BigDecimal price = new BigDecimal(99.99);

        ConcertTicket nonEmptyTicket = new ConcertTicket("123", "Plaza", 123, true, 'C', weight, price);
        tickets = Arrays.asList(nonEmptyTicket);

        // Setting ID to ANY class (goal 1)
        TicketService id = new TicketService();
        id.setTicketUid(1);
        System.out.println("class id: id.getConcertTicketId()");
        System.out.println();


        // Printing contents (goal 2 - default and overridden)
        System.out.println("Print content:");
        id.printContent();
        System.out.println("Overridden print content");
        nonEmptyTicket.printContent();

        // Setting time and sector (goal 3)
        nonEmptyTicket.setTicketTime(1231123);
        nonEmptyTicket.setSector('A');
        System.out.println("All values from Ticket:");
        System.out.println(nonEmptyTicket.getAllValues());
        System.out.println();

        // .share() method (goal 4)
        nonEmptyTicket.share("88005553535");
        nonEmptyTicket.share("88005553535","patrickjane@mentalist.com");

        // getTicket() and checkTicket(), printRole() (goal 5)
        User user = new User();
        Admin admin = new Admin();
        user.printRole();
        user.getTicket(nonEmptyTicket);
        user.printContent();
        admin.printRole();
        admin.checkTicket(nonEmptyTicket);
        admin.printContent();



    }

    public static ConcertTicket getTicketByStadiumSector(char sector) {
        return tickets.stream()
                .filter(ticket -> ticket.getSector() == sector)
                .findAny()
                .orElse(null);
    }

}
