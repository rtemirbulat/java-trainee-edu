package com.rtemi.model.entity;

import com.rtemi.interfaces.Printable;
import com.rtemi.model.ConcertTicket;
import com.rtemi.model.TicketUID;
import com.rtemi.model.enums.Role;

public class User extends TicketUID implements Printable {
    final Role role;
    boolean ticketAcquired = false;
    public User(){
        this.role = Role.CLIENT;
    }


    public void printRole(){
        System.out.println("Role described :" + role);
    }

    //
    public void getTicket(ConcertTicket ticket){
        ticketAcquired = true;
        System.out.println("Ticket acquired: " + ticket.getConcertTicketId());
    }
    @Override
    public void printContent() {
        System.out.println("Role :" + role + " Ticket checked :" + ticketAcquired );
    }
}
