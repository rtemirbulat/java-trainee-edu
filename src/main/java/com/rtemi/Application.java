package com.rtemi;

import com.rtemi.config.Config;
import com.rtemi.dao.Ticket;
import com.rtemi.dao.TicketDAO;
import com.rtemi.dao.User;
import com.rtemi.dao.UserDAO;
import com.rtemi.model.enums.TicketType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class Application {
    private static final Logger LOGGER = getLogger(Application.class.getSimpleName());
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        UserDAO userDAO = context.getBean(UserDAO.class);
        TicketDAO ticketDAO = context.getBean(TicketDAO.class);
        LocalDateTime localDateTime = LocalDateTime.now();
        userDAO.saveUser(new User(1,"John",localDateTime));
        ticketDAO.saveTicket(new Ticket(1,1, TicketType.DAY,localDateTime));

    }
}
