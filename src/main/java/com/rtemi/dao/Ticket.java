package com.rtemi.dao;

import com.rtemi.model.enums.TicketType;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="user_id")
    private int userId;
    @Column(name="ticket_type")
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private TicketType ticketType;
    @Column(name="creation_date")
    private LocalDateTime creationTime;

    public Ticket(){

    }

    public Ticket(int id, int userId, TicketType ticketType, LocalDateTime creationTime) {
        this.id = id;
        this.userId = userId;
        this.ticketType = ticketType;
        this.creationTime = creationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", user_id=" + userId +
                ", ticketType=" + ticketType +
                ", creationTime=" + creationTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && userId == ticket.userId && ticketType == ticket.ticketType && Objects.equals(creationTime, ticket.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, ticketType, creationTime);
    }
}