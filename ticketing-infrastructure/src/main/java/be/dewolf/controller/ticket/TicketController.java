package be.dewolf.controller.ticket;

import be.dewolf.domain.day.DayService;
import be.dewolf.domain.day.DayView;
import be.dewolf.domain.ticket.Priority;
import be.dewolf.domain.ticket.command.CreateTicketCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private DayService dayService;

    private List<TicketDTO> tickets;

    @Autowired
    public TicketController(DayService dayService) {
        this.dayService = dayService;
        this.tickets = new ArrayList<>();
        TicketDTO ticket1 = TicketDTO.builder()
                                     .assignedUser("yannis de wolf")
                                     .description("doe iets")
                                     .priority(Priority.MEDIUM)
                                     .deadLine(LocalDate.now()
                                                        .plusDays(20))
                                     .project("eerste")
                                     .build();

        TicketDTO ticket2 = TicketDTO.builder()
                                     .assignedUser("yannis de wolf")
                                     .description("doe iets anders")
                                     .priority(Priority.HIGH)
                                     .deadLine(LocalDate.now()
                                                        .plusDays(10))
                                     .build();


        tickets.addAll(Arrays.asList(ticket1, ticket2));
    }

    @GetMapping("/dayviews")
    public ModelAndView getTickets() {
        System.out.println("hello world");

        List<DayView> dayViews = dayService.getDayViews();


        return new ModelAndView("components/ticket/dayviews", "calendarView",
                                dayViews);
    }


    @PostMapping("/create")
    public ModelAndView createTicket(@ModelAttribute CreateTicketCommand createTicket) {
        this.tickets.add(TicketDTO.builder()
                                  .deadLine(createTicket.getDeadline())
                                  .priority(createTicket.getPriority())
                                  .assignedUser(createTicket.getAssignedUserId() + "")
                                  .description(createTicket.getDescription())
                                  .build());
        return new ModelAndView("redirect:/ticket/all");
    }

}
