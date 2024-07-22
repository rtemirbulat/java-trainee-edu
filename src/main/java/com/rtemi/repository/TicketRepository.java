package com.rtemi.repository;

import com.rtemi.dao.Ticket;
import com.rtemi.model.enums.TicketType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Transactional
@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer>{
    @Modifying
    @Query(value = "INSERT INTO ticket(user_id, ticket_type, creation_date) VALUES(:user_id, CAST(:ticket_type AS ticket_type), :creation_date)",
            nativeQuery = true)
    Integer save(@Param("user_id") int userId, @Param("ticket_type") TicketType ticketType, @Param("creation_date") LocalDateTime creationDate);

    @Modifying
    @Query(value = "UPDATE ticket SET user_id = :user_id, ticket_type = CAST(:ticket_type AS ticket_type), creation_date = :creation_date WHERE id = :id",
            nativeQuery = true)
    Integer update(@Param("ticket_id") int ticketId, @Param("user_id") int userId, @Param("ticket_type") TicketType ticketType, @Param("creation_date") LocalDateTime creationDate);

    List<Ticket> findByUserId(int userId);
}
