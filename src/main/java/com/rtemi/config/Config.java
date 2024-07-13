package com.rtemi.config;


import com.rtemi.dao.TicketDAO;
import com.rtemi.dao.UserDAO;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Config {
    @Bean
    public DataSource retrieveDataSource(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser("postgres");
        dataSource.setPassword("6OHb4UakpeCCrBZq35ag");
        dataSource.setURL("jdbc:postgresql://database-2.ctcue0gs2qe2.eu-central-1.rds.amazonaws.com:5432/my_ticket_service_db");
        return dataSource;
    }
}
