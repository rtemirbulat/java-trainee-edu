package com.rtemi.dao;

import com.rtemi.model.enums.TicketType;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAO {
    private final boolean activated;
    public UserDAO(boolean activated) {
        this.activated = activated;
    }

    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public User getUserById(int id) {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        }
    }

    public void deleteUserById(int id) {
        Transaction transaction = null;
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void retrieveAllUsers() {
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            List<User> users = session.createQuery("from User", User.class).list();
            System.out.println("ID | Name       | Creation Date");
            System.out.println("---|------------|--------------------------");
            for (User user : users) {
                System.out.printf("%-3d| %-10s | %-25s%n", user.getId(), user.getName(), user.getCreationDate());
            }
        }
    }

    public void updateUserAndTicketType(User user, TicketType ticketType) {
        Transaction transaction = null;
        try (Session session = SessionFactoryProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            TicketDAO ticketDAO = new TicketDAO();
            List<Ticket> tickets = ticketDAO.getTicketsByUserId(user.getId());
            for (Ticket ticket : tickets) {
                ticket.setTicketType(ticketType);
                session.merge(ticket);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
