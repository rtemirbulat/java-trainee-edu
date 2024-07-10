package com.rtemi.dao;
import com.rtemi.model.Ticket;
import com.rtemi.model.User;
import com.rtemi.model.enums.TicketType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserDAO {
    private SessionFactory sessionFactory;

    public UserDAO(){
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void saveUser(User user){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e){
            if (transaction!=null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    public User getUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }
    public void deleteUserById(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
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
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session.createQuery("from User", User.class).list();
            System.out.println("ID | Name       | Creation Date");
            System.out.println("---|------------|--------------------------");
            for (User user : users) {
                System.out.printf("%-3d| %-10s | %-25s%n", user.getId(), user.getName(), user.getCreationDate());
            }
        }
    }
    public void updateUserAndTickets(User user, TicketType ticketType) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
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
