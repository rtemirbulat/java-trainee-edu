package com.rtemi.services;

import com.rtemi.dao.Ticket;
import com.rtemi.model.enums.TicketType;
import com.rtemi.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    public Ticket saveTicket(Ticket ticket){
        ticketRepository.save(ticket.getUserId(),ticket.getTicketType(),ticket.getCreationTime());
        return ticket;
    }
    public Ticket getTicketById(int ticketId){
        return ticketRepository.findById(ticketId)
                .orElseThrow(()->new IllegalArgumentException("Ticket with id not found"));

    }

    public List<Ticket> getTicketsByUserId(int userId) {
        return ticketRepository.findByUserId(userId);
    }
    public Ticket updateTicketType(int ticketId, TicketType ticketType) {
        Ticket ticketToUpdate = getTicketById(ticketId);
        ticketToUpdate.setTicketType(ticketType);
        ticketRepository.update(ticketToUpdate.getId(),ticketToUpdate.getUserId(),
                ticketToUpdate.getTicketType(),ticketToUpdate.getCreationTime());
        return ticketToUpdate;
    }
    public void deleteTicketsByTicketId(int ticketId){
        getTicketById(ticketId);
        ticketRepository.deleteById(ticketId);
    }
}
