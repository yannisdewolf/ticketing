package be.dewolf.domain.ticket;

import be.dewolf.domain.ticket.Ticket;
import org.assertj.core.api.LocalDateAssert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> getAllByProjectName(String name);

    List<Ticket> getByDeadlineBetween(LocalDate afterThis, LocalDate beforeThis);

}
