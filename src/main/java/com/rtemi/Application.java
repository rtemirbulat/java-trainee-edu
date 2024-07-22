package com.rtemi;


import com.rtemi.config.Config;
import com.rtemi.controller.TicketController;
import com.rtemi.services.TicketService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        TicketService ticketService = applicationContext.getBean(TicketService.class);

    }
}
