package com.rtemi.dao;

import com.rtemi.model.enums.TicketType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

public class TicketDAO {

    public TicketDAO() {
    }

    private SessionFactory sessionFactory;
    @Autowired
    public TicketDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveTicket(Ticket ticket) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            String query = "INSERT INTO public.Ticket (user_id, ticket_type, creation_date) VALUES (:userId, :ticketType, :creationDate)";
            NativeQuery<?> q = session.createNativeQuery(query);
            q.setParameter("userId", ticket.getUserId());
            q.setParameter("ticketType", ticket.getTicketType());
            q.setParameter("creationDate", ticket.getCreationTime());
            q.executeUpdate();

            transaction.commit();
            System.out.println("Ticket saved");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Ticket getTicketById(int id) {
        return sessionFactory.openSession().get(Ticket.class, id);
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            var hql = "FROM" + Ticket.class.getCanonicalName() + "T WHERE T.userId = : userId";
            var query = session.createSelectionQuery(hql, Ticket.class);
            query.setParameter("userId", userId);
            return query.list();
        }
    }

    public void updateTicketType(int id, TicketType ticketType) {
        Session session = sessionFactory.openSession();
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
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<Ticket> tickets = getTicketsByUserId(userId);
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
        try (Session session = sessionFactory.openSession()) {
            List<Ticket> tickets = session.createQuery("from Ticket", Ticket.class).list();
            System.out.println("ID | User ID | Ticket Type | Creation Date");
            System.out.println("---|---------|-------------|--------------------------");
            for (Ticket ticket : tickets) {
                System.out.printf("%-3d| %-7d | %-11s | %-25s%n", ticket.getId(), ticket.getUserId(), ticket.getTicketType(), ticket.getCreationTime());
            }
        }
    }
}
