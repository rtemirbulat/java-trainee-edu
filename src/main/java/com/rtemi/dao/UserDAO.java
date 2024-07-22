package com.rtemi.dao;

import com.rtemi.model.enums.Status;
import com.rtemi.model.enums.TicketType;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAO {
    private final boolean activated;

    private static SessionFactory sessionFactory;


    public UserDAO(boolean activated, SessionFactory sessionFactory) {
        this.activated = activated;
        this.sessionFactory = sessionFactory;
    }




    @Transactional
    public void updateUserAndSaveTickets(User user, Ticket ticket){
        if(!activated) {
            throw new UnsupportedOperationException("Update not supported.");
        }
        if (user == null) {
            throw new IllegalArgumentException("User must not be null.");
        }
        if (ticket != null) {
            UserDAO.updateUserAndSaveTicket(user, ticket);
        } else {
            throw new IllegalArgumentException("Ticket must not be null.");
        }
    }

    public void saveUser(User user) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(user);
                transaction.commit();
                System.out.println("User created");
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
    }

    public User getUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            System.out.println("getUserById compiled");
            return session.get(User.class, id);
        }
    }

    public void deleteUserById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                System.out.println("deleteUserById is called");
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
        try (Session session = sessionFactory.openSession()) {
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
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            TicketDAO ticketDAO = new TicketDAO();
            List<Ticket> tickets = ticketDAO.getTicketsByUserId(user.getId());
            for (Ticket ticket : tickets) {
                System.out.println("updateUserAndTicketType is called");
                ticket.setTicketType(ticketType);
                session.merge(ticket);
                System.out.println("updateUserAndTicketType is completed");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    public static void updateUserAndSaveTicket(User user, Ticket ticket) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            if (user.getStatus() == Status.ACTIVATED) {
                System.out.println("updateUserAndSaveTicket is called");
                session.merge(user);
                ticket.setUserId(user.getId());
                session.persist(ticket);
                System.out.println("updateUserAndSaveTicket is completed");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
