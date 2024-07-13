package com.rtemi.config;


import com.rtemi.connection.ConnectionPostgres;
import com.rtemi.dao.UserDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class Config {
    @Bean
    public ConnectionPostgres connectionPostgres(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password){

        return new ConnectionPostgres(url,username,password);
    }
    @Bean
    public UserDAO userDAO(@Value("${service.update-enabled:true") boolean activated){
        return new UserDAO(activated);
    }
}
