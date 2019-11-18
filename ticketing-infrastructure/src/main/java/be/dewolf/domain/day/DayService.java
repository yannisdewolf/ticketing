package be.dewolf.domain.day;

import be.dewolf.domain.ticket.Ticket;
import be.dewolf.domain.ticket.TicketService;
import be.dewolf.domain.ticket.controller.CalendarFilter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
                           .getDayViews(YearMonth.now()
                                                 .atDay(1), YearMonth.now()
                                                                     .atEndOfMonth());

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

    public List<DayView> getDayViews(Map<String, String> parameters, CalendarFilter calendarFilter) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate begindate = LocalDate.parse(parameters.get("begindate"), dateFormatter);
        LocalDate enddate = LocalDate.parse(parameters.get("enddate"), dateFormatter);

        return CalendarView.from(this.ticketService.getTickets(parameters)
                                            .stream()
                                            .map(ticketToTicketInfo())
                                            .collect(Collectors.toList()))
                    .getDayViews(begindate, enddate);
    }
}
