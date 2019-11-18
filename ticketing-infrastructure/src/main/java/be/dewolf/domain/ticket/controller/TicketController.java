package be.dewolf.domain.ticket.controller;

import be.dewolf.domain.common.ValueDto;
import be.dewolf.domain.day.DayService;
import be.dewolf.domain.day.DayView;
import be.dewolf.domain.project.Project;
import be.dewolf.domain.project.ProjectService;
import be.dewolf.domain.ticket.TicketService;
import be.dewolf.domain.ticket.command.CreateTicketCommand;
import be.dewolf.domain.user.UserService;
import be.dewolf.domain.user.controller.UserView;
import org.assertj.core.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
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
        model.addObject("projects", projectService.getAllProjects()
                                                  .stream()
                                                  .map(this::toDto)
                                                  .collect(Collectors.toList()));


        return model;
        //return new ModelAndView("components/ticket/newTicket");
    }

    @PostMapping("/create")
    public ModelAndView createTicket(@ModelAttribute CreateTicketCommand createTicket) {
        System.out.println();
        ticketService.createTicket(createTicket);
        return new ModelAndView("redirect:/ticket/agenda");
    }

    private ValueDto toDto(Project project) {
        return ValueDto.builder()
                       .id(project.getId())
                       .name(project.getName())
                       .build();
    }

    private ValueDto toDto(UserView user) {
        return ValueDto.builder()
                       .id(user.getId())
                       .name(user.getName())
                       .build();
    }

    @GetMapping("/agenda")
    public ModelAndView getTickets() {


        List<DayView> dayViews = dayService.getDayViews();


        ModelAndView calendarView = new ModelAndView("components/ticket/agenda", "calendarView",
                                                     dayViews);
        calendarView.addObject("projects", projectService.getAllProjects()
                                                         .stream()
                                                         .map(this::toDto)
                                                         .collect(Collectors.toList()));

        CalendarFilter calendarFilter = CalendarFilter.builder()
                                             .begindate("01/11/2019")
                                             .enddate("30/11/2019")
                                             .build();

        calendarView.addObject("calendarFilter", calendarFilter );

        return calendarView;
    }

    @PostMapping("/agenda/search")
    public ModelAndView getTickets(@ModelAttribute CalendarFilter calendarFilter) {

        Map<String, String> parameters = Maps.newHashMap("begindate", calendarFilter.getBegindate());
        parameters.put("enddate", calendarFilter.getEnddate());
        if (!StringUtils.isEmpty(calendarFilter.getProject())) {
            parameters.put("project", calendarFilter.getProject());
        }

        List<DayView> dayViews = dayService.getDayViews(parameters, calendarFilter);


        ModelAndView calendarView = new ModelAndView("components/ticket/agenda", "calendarView",
                                                     dayViews);
        calendarView.addObject("projects", projectService.getAllProjects()
                                                         .stream()
                                                         .map(this::toDto)
                                                         .collect(Collectors.toList()));

        calendarView.addObject("calendarFilter", calendarFilter);
        return calendarView;
    }


}
