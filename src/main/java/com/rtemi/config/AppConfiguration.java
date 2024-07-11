package com.rtemi.config;

import com.rtemi.dao.TicketDAO;
import com.rtemi.dao.UserDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.postgresql.ds.PGSimpleDataSource;
import javax.sql.DataSource;

@Configuration
public class AppConfiguration {
    @Bean
    public DataSource dataSource(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser("");
        dataSource.setPassword("");
        dataSource.setURL("");
        return dataSource;
    }
    @Bean
    public UserDAO userDAO(){
        return new UserDAO(dataSource());
    }
    @Bean
    public TicketDAO ticketDAO(){
        return new TicketDAO(dataSource());
    }

}
