package com.rtemi.config;



import com.rtemi.dao.Ticket;
import com.rtemi.dao.TicketDAO;
import com.rtemi.dao.User;
import com.rtemi.dao.UserDAO;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@Configuration
@PropertySource("classpath:application.properties")
public class Config {
    private final Environment env;

    @Autowired
    public Config(Environment env) {
        this.env = env;
    }
    
    @Bean
    public SessionFactory sessionFactory(){
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.setProperty("hibernate.dialect",env.getProperty("hibernate.dialect"));
        configuration.setProperty("hibernate.connection.driver_class",env.getProperty("hibernate.connection.driver_class"));
        configuration.setProperty("hibernate.connection.url",env.getProperty("hibernate.connection.url"));
        configuration.setProperty("hibernate.connection.username",env.getProperty("hibernate.connection.username"));
        configuration.setProperty("hibernate.connection.password",env.getProperty("hibernate.connection.password"));
        configuration.setProperty("hibernate.show_sql",env.getProperty("hibernate.show_sql"));
        configuration.setProperty("hibernate.hbm2ddl",env.getProperty("hibernate.hbm2ddl"));
        configuration.addAnnotatedClass(Ticket.class);
        configuration.addAnnotatedClass(User.class);
        return configuration.buildSessionFactory();
    }
    @Bean
    public UserDAO userDAO(@Value("${service.update-enabled:true}") boolean activated){
        return new UserDAO(activated,sessionFactory());
    }
    @Bean
    public TicketDAO ticketDAO(){
        return new TicketDAO(sessionFactory());
    }

}
