package com.rtemi.controller;

import com.rtemi.dao.Ticket;
import com.rtemi.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    @Autowired(required = false)
    private String conditionalBean;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/{ticketId}")
    public Ticket getTicketById(@PathVariable("ticketId") int id) {
        System.out.println(conditionalBean);
        return ticketService.getTicketById(id);
    }
}
