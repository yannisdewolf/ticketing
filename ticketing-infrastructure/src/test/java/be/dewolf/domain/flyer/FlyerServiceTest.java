package be.dewolf.domain.flyer;

import be.dewolf.domain.ticket.Ticket;
import static org.assertj.core.api.Assertions.*;

import be.dewolf.domain.ticket.command.CreateTicketCommand;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FlyerServiceTest {

    @Test
    public void createFlyer() {


        CreateFlyerCommand fc = new CreateFlyerCommand(
                1L,
                LocalDate.of(2020, 4, 1),
                LocalDate.of(2020, 3, 18),
                LocalDate.of(2020, 3, 2),
                LocalDate.of(2020, 1, 28),
                LocalDate.of(2020, 1, 22),
                LocalDate.of(2020,1,14)
        );

        DateCalculationService dateCalculationService = new DateCalculationService();
        List<CreateTicketCommand> actualCreatedTickets = new FlyerService(dateCalculationService).createTickets(fc);

        actualCreatedTickets.sort(Comparator.comparing(CreateTicketCommand::getDeadline));

        assertThat(actualCreatedTickets).hasSize(42);

        String collect = actualCreatedTickets.stream()
                                             .map(t -> t.getTitle() + " " + t.getDeadline())
                                             .collect(Collectors.joining("\r\n"));

        System.out.println(collect);
    }

}
