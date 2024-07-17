package com.rtemi;

import com.rtemi.config.Config;
import com.rtemi.dao.Ticket;
import com.rtemi.dao.TicketDAO;
import com.rtemi.dao.User;
import com.rtemi.dao.UserDAO;
import com.rtemi.model.BusTicket;
import com.rtemi.model.enums.Status;
import com.rtemi.model.enums.TicketType;
import com.rtemi.utils.TicketLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class Application {
    private static final Logger LOGGER = getLogger(Application.class.getSimpleName());
    public static final String path = "src/main/resources/tickets.json";
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        UserDAO userDAO = context.getBean(UserDAO.class);
        TicketDAO ticketDAO = context.getBean(TicketDAO.class);
        LocalDateTime localDateTime = LocalDateTime.now();



        User user = new User(1,"John",localDateTime);
        Ticket ticket = new Ticket(8,15, TicketType.DAY,localDateTime);

        userDAO.saveUser(user);
        ticketDAO.saveTicket(ticket);

        User user2 = new User(2,"Ali",localDateTime);
        Ticket ticket2 = new Ticket(2,2,TicketType.DAY,localDateTime);
        userDAO.saveUser(user2);
        user2.setStatus(Status.DISABLED);
        userDAO.updateUserAndSaveTickets(user2, ticket2);

        TicketLoader<BusTicket> loader = new TicketLoader<>();
        List<BusTicket> tickets = loader.load(path, BusTicket.class);
        for (BusTicket t: tickets) {
            System.out.println(t.toString());
        }

    }
}
