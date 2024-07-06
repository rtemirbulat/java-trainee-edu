package com.rtemi.model;

import com.rtemi.model.enums.TicketType;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table (name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type")
    private TicketType ticketType;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    public Ticket(int id, User user, TicketType ticketType, Timestamp creationDate) {
        this.id = id;
        this.user = user;
        this.ticketType = ticketType;
        this.creationDate = creationDate;
    }

    public Ticket(int id, TicketType ticketType) {
        this.id = id;
        this.ticketType = ticketType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ticket() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }


    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
