package com.rtemi.dao;
import com.rtemi.model.Ticket;
import com.rtemi.model.enums.TicketType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    private SessionFactory sessionFactory;

    public TicketDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    public void saveTicket(Ticket ticket) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(ticket);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public Ticket getTicketById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Ticket.class, id);
        }
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
           return session.createQuery("FROM Ticket WHERE user.id = :userId  ", Ticket.class)
                   .setParameter("userId", userId)
                    .list();
        }
    }

    public void updateTicketType(int id, TicketType ticketType) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, id);
            if (ticket != null) {
                ticket.setTicketType(ticketType);
                session.merge(ticket);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteTicketsByUserId(int userId) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
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
            e.printStackTrace();
        }
    }

    public void retrieveAllTickets() {
        try (Session session = sessionFactory.openSession()) {
            List<Ticket> tickets = session.createQuery("from Ticket", Ticket.class).list();
            System.out.println("ID | User ID | Ticket Type | Creation Date");
            System.out.println("---|---------|-------------|--------------------------");
            for (Ticket ticket : tickets) {
                System.out.printf("%-3d| %-7d | %-11s | %-25s%n", ticket.getId(), ticket.getUser().getId(), ticket.getTicketType(), ticket.getCreationDate());
            }
        }
    }


}
