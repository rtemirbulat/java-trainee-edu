package com.rtemi;

import com.rtemi.dao.Ticket;
import com.rtemi.model.enums.TicketType;
import com.rtemi.repository.TicketRepository;
import com.rtemi.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private int validTicketId = 1;
    private int invalidTicketId = -1;
    private int validUserId = 1;
    private int invalidUserId = -1;

    @BeforeEach
    public void setUp() {
        ticket = new Ticket(validTicketId, validUserId, TicketType.DAY , LocalDateTime.now());
    }

    @Test
    void saveTicket_positive() {
        when(ticketRepository.save(ticket.getUserId(), ticket.getTicketType(), ticket.getCreationTime()))
                .thenReturn(ticket.getId());
        ticketService.saveTicket(ticket);
        verify(ticketRepository, times(1)).save(ticket.getUserId(), ticket.getTicketType(), ticket.getCreationTime());
    }

    @Test
    void saveTicket_negative() {
        doThrow(new IllegalArgumentException()).when(ticketRepository).save(ticket.getUserId(), ticket.getTicketType(), ticket.getCreationTime());
        assertThrows(IllegalArgumentException.class, () -> ticketService.saveTicket(ticket));
    }

    @Test
    void saveTicket_cornerCase() {
        Ticket cornerCaseTicket = new Ticket(0, 0, TicketType.DAY, LocalDateTime.now().minusYears(100));
        when(ticketRepository.save(cornerCaseTicket.getUserId(), cornerCaseTicket.getTicketType(), cornerCaseTicket.getCreationTime()))
                .thenReturn(cornerCaseTicket.getId());
        ticketService.saveTicket(cornerCaseTicket);
        verify(ticketRepository, times(1)).save(cornerCaseTicket.getUserId(), cornerCaseTicket.getTicketType(), cornerCaseTicket.getCreationTime());
    }

    @Test
    void getTicketById_positive() {
        when(ticketRepository.findById(validTicketId)).thenReturn(Optional.of(ticket));
        Ticket foundTicket = ticketService.getTicketById(validTicketId);
        assertEquals(ticket, foundTicket);
    }

    @Test
    void getTicketById_negative() {
        when(ticketRepository.findById(invalidTicketId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> ticketService.getTicketById(invalidTicketId));
    }

    @Test
    void getTicketById_cornerCase() {
        Ticket cornerCaseTicket = new Ticket(0, 0, TicketType.DAY, LocalDateTime.now());
        when(ticketRepository.findById(0)).thenReturn(Optional.of(cornerCaseTicket));
        Ticket foundTicket = ticketService.getTicketById(0);
        assertEquals(cornerCaseTicket, foundTicket);
    }

    @Test
    void getTicketsByUserId_positive() {
        List<Ticket> tickets = Arrays.asList(ticket);
        when(ticketRepository.findByUserId(validUserId)).thenReturn(tickets);
        List<Ticket> foundTickets = ticketService.getTicketsByUserId(validUserId);
        assertEquals(tickets, foundTickets);
    }

    @Test
    void getTicketsByUserId_negative() {
        when(ticketRepository.findByUserId(invalidUserId)).thenReturn(Arrays.asList());
        List<Ticket> foundTickets = ticketService.getTicketsByUserId(invalidUserId);
        assertTrue(foundTickets.isEmpty());
    }

    @Test
    void getTicketsByUserId_cornerCase() {
        List<Ticket> tickets = Arrays.asList(ticket);
        when(ticketRepository.findByUserId(0)).thenReturn(tickets);
        List<Ticket> foundTickets = ticketService.getTicketsByUserId(0);
        assertEquals(tickets, foundTickets);
    }

    @Test
    void updateTicketType_positive() {
        when(ticketRepository.findById(validTicketId)).thenReturn(Optional.of(ticket));
        when(ticketRepository.update(validTicketId, ticket.getUserId(), TicketType.MONTH, ticket.getCreationTime()))
                .thenReturn(1);
        Ticket updatedTicket = ticketService.updateTicketType(validTicketId, TicketType.MONTH);
        assertEquals(TicketType.MONTH, updatedTicket.getTicketType());
    }

    @Test
    void updateTicketType_negative() {
        when(ticketRepository.findById(invalidTicketId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> ticketService.updateTicketType(invalidTicketId, TicketType.MONTH));
    }

    @Test
    void updateTicketType_cornerCase() {
        Ticket cornerCaseTicket = new Ticket(0, 0, TicketType.DAY, LocalDateTime.now());
        when(ticketRepository.findById(0)).thenReturn(Optional.of(cornerCaseTicket));
        when(ticketRepository.update(0, 0, TicketType.MONTH, LocalDateTime.now())).thenReturn(1);
        Ticket updatedTicket = ticketService.updateTicketType(0, TicketType.MONTH);
        assertEquals(TicketType.MONTH, updatedTicket.getTicketType());
    }

    @Test
    void deleteTicketsByTicketId_positive() {
        when(ticketRepository.findById(validTicketId)).thenReturn(Optional.of(ticket));
        doNothing().when(ticketRepository).deleteById(validTicketId);
        ticketService.deleteTicketsByTicketId(validTicketId);
        verify(ticketRepository, times(1)).deleteById(validTicketId);
    }

    @Test
    void deleteTicketsByTicketId_negative() {
        when(ticketRepository.findById(invalidTicketId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> ticketService.deleteTicketsByTicketId(invalidTicketId));
    }

    @Test
    void deleteTicketsByTicketId_cornerCase() {
        Ticket cornerCaseTicket = new Ticket(0, 0, TicketType.DAY, LocalDateTime.now());
        when(ticketRepository.findById(0)).thenReturn(Optional.of(cornerCaseTicket));
        doNothing().when(ticketRepository).deleteById(0);
        ticketService.deleteTicketsByTicketId(0);
        verify(ticketRepository, times(1)).deleteById(0);
    }
}