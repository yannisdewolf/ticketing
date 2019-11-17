package be.dewolf.domain.ticket;

import be.dewolf.domain.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
