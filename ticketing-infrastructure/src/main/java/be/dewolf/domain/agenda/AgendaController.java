package be.dewolf.domain.agenda;

import be.dewolf.domain.ticket.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/agenda")
public class AgendaController {

    private TicketService ticketService;

    public AgendaController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ModelAndView getTickets() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<CalendarItem> calendarItems = this.ticketService.allTickets().stream()
        .map(ticket -> {
            return new CalendarItem(dateTimeFormatter.format(ticket.getDeadline()), ticket.getTitle());
        }).collect(Collectors.toList());

        ModelAndView modelAndView = new ModelAndView("components/agenda/agenda", "begroeting", "hello world!");
        modelAndView.addObject("calendarItems", calendarItems);
        return modelAndView;
    }

}
