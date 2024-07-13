package com.rtemi.dao;

import com.rtemi.dao.Ticket;
import com.rtemi.model.enums.TicketType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class TicketDAO {

    public TicketDAO() {
    }

    public void saveTicket(Ticket ticket) {
        var session = SessionFactoryProvider.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        session.persist(ticket);
        transaction.commit();
        session.close();
    }

    public Ticket getTicketById(int id) {
        return SessionFactoryProvider.getSessionFactory().openSession().get(Ticket.class, id);
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return session.createQuery("FROM Ticket WHERE user.id = :userId  ", Ticket.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }

    public void updateTicketType(int id, TicketType ticketType) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        var ticket = session.get(Ticket.class, id);
        if (ticketType != null) {
            ticket.setTicketType(ticketType);
            session.merge(ticket);
            transaction.commit();
            session.close();
        }
    }

    public void deleteTicketsByUserId(int userId) {
        Transaction transaction = null;
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Ticket> tickets = session.createQuery("from Ticket where user.id = :userId", Ticket.class)
                    .setParameter("userId", userId)
                    .list();
            for (Ticket ticket : tickets) {
                session.remove(ticket);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void retrieveAllTickets() {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            List<Ticket> tickets = session.createQuery("from Ticket", Ticket.class).list();
            System.out.println("ID | User ID | Ticket Type | Creation Date");
            System.out.println("---|---------|-------------|--------------------------");
            for (Ticket ticket : tickets) {
                System.out.printf("%-3d| %-7d | %-11s | %-25s%n", ticket.getId(), ticket.getUserId(), ticket.getTicketType(), ticket.getCreationTime());
            }
        }
    }
}
