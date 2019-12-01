package be.dewolf.domain.flyer;

import be.dewolf.domain.project.Project;
import be.dewolf.domain.project.ProjectService;
import be.dewolf.domain.ticket.TicketService;
import be.dewolf.domain.ticket.command.CreateTicketCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/flyer")
public class FlyerController {

    private FlyerService flyerService;
    private ProjectService projectService;
    private TicketService ticketService;

    public FlyerController(FlyerService flyerService, ProjectService projectService, TicketService ticketService) {
        this.flyerService = flyerService;
        this.projectService = projectService;
        this.ticketService = ticketService;
    }

    @GetMapping("/init")
    public ModelAndView createTicketsForFlyer() {


        ModelAndView createFlyerCommand = new ModelAndView("components/flyer/init", "createFlyerCommand", new CreateFlyerCommand());

        createFlyerCommand.addObject("projects", projectService.getAllProjects()
                                                  .stream()
                                                  .map(Project::toDto)
                                                  .collect(Collectors.toList()));


        return createFlyerCommand;
    }

    @PostMapping("/init")
    public ModelAndView createTicketsForFlyer(@ModelAttribute(name = "createFlyerCommand")  CreateFlyerCommand createFlyerCommand) {
        List<CreateTicketCommand> tickets = this.flyerService.createTickets(createFlyerCommand);
        tickets.forEach(ticketService::createTicket);
        return new ModelAndView("redirect:/ticket/agenda");
    }

}
