package com.rtemi.model.entity;

import com.rtemi.interfaces.Printable;
import com.rtemi.model.ConcertTicket;
import com.rtemi.model.TicketUID;
import com.rtemi.model.enums.Role;

public class Admin extends TicketUID implements Printable {
    final Role role;
    private boolean ticketApproved = false;
    public Admin(){
        this.role = Role.ADMIN;
    }
    public void printRole(){
        System.out.println("Role described :" + role);
    }

    public void checkTicket(ConcertTicket ticket){
        ticketApproved = true;
        System.out.println("Checked ticket : " + ticket.getConcertTicketId());
    }
    @Override
    public void printContent() {
        System.out.println("Role :" + role + " Ticket checked :" + ticketApproved );
    }


}
