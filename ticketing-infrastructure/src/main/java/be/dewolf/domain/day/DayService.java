package be.dewolf.domain.day;

import be.dewolf.domain.ticket.Ticket;
import be.dewolf.domain.ticket.TicketService;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DayService {

    public static final String PROJECT_1 = "project1";
    private static final String PROJECT_2 = "project2";

    private TicketService ticketService;

    public DayService(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    public List<DayView> getDayViews() {

        List<TicketInfo> allTicketInfos =
                this.ticketService.getTickets(YearMonth.now())
                                  .stream()
                                  .map(ticketToTicketInfo())
                                  .collect(Collectors.toList());

        return CalendarView.from(allTicketInfos)
                           .getDayViews();

    }

    private Function<Ticket, TicketInfo> ticketToTicketInfo() {
        return ticket -> TicketInfo.builder()
                                   .assignee(ticket.getUser()
                                                   .getName())
                                   .project(ticket.getProject()
                                                  .getName())
                                   .title(ticket.getTitle())
                                   .deadline(ticket.getDeadline())
                                   .build();
    }

    public List<DayView> getDayViews(Map<String, String> parameters) {
        return CalendarView.from(this.ticketService.getTickets(parameters)
                                            .stream()
                                            .map(ticketToTicketInfo())
                                            .collect(Collectors.toList()))
                    .getDayViews();
    }
}
