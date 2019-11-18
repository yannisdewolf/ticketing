package be.dewolf.domain.ticket.controller;

import be.dewolf.controller.user.ValueDto;
import be.dewolf.domain.day.DayService;
import be.dewolf.domain.day.DayView;
import be.dewolf.domain.project.Project;
import be.dewolf.domain.project.ProjectService;
import be.dewolf.domain.ticket.TicketService;
import be.dewolf.domain.ticket.command.CreateTicketCommand;
import be.dewolf.domain.user.User;
import be.dewolf.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private DayService dayService;
    private UserService userService;
    private TicketService ticketService;
    private ProjectService projectService;

    @Autowired
    public TicketController(DayService dayService, UserService userService, TicketService ticketService, ProjectService projectService) {
        this.dayService = dayService;
        this.userService = userService;

        this.ticketService = ticketService;
        this.projectService = projectService;
    }

    @GetMapping("/create")
    public ModelAndView createTicket() {
        ModelAndView model = new ModelAndView("components/ticket/newTicket", "ticket", new CreateTicketCommand());
        model.addObject("users", userService.findAll()
                                            .stream()
                                            .map(this::toDto)
                                            .collect(Collectors.toList()));
        model.addObject("projects", projectService.getAllProjects().stream().map(this::toDto).collect(Collectors.toList()));

        return model;
        //return new ModelAndView("components/ticket/newTicket");
    }

    private ValueDto toDto(Project project) {
        return ValueDto.builder()
                       .id(project.getId())
                       .name(project.getName())
                       .build();
    }

    private ValueDto toDto(User user) {
        return ValueDto.builder()
                       .id(user.getId())
                       .name(user.getName())
                       .build();
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
        System.out.println();
        ticketService.createTicket(createTicket);
        return new ModelAndView("redirect:/ticket/dayviews");
    }

}
