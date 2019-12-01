package be.dewolf.domain.ticket;

import be.dewolf.domain.project.ProjectRepository;
import be.dewolf.domain.ticket.command.CreateTicketCommand;
import be.dewolf.domain.user.GroupRepository;
import be.dewolf.domain.user.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class TicketService {

    private TicketRepository ticketRepository;
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    public TicketService(TicketRepository ticketRepository, GroupRepository groupRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.ticketRepository = ticketRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public Ticket createTicket(CreateTicketCommand createTicketCommand) {
        Ticket.TicketBuilder builder = Ticket.builder();
        if (createTicketCommand.getAssignedGroupId() != null) {
            builder.assignedGroup(groupRepository.getOne(createTicketCommand.getAssignedGroupId()));
        }
        if (createTicketCommand.getAssignedUserId() != null) {
            builder.user(userRepository.getOne(createTicketCommand.getAssignedUserId()));
        }

        builder.project(projectRepository.getOne(createTicketCommand.getAssignedProjectId()));
        builder.title(createTicketCommand.getTitle());
        builder.description(createTicketCommand.getDescription());
        builder.deadline(createTicketCommand.getDeadline());
        builder.priority(createTicketCommand.getPriority());
        return ticketRepository.save(builder.build());

    }

    public List<Ticket> allTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getTickets(YearMonth yearMonth) {
        return ticketRepository.getByDeadlineBetween(yearMonth.atDay(1), yearMonth.atEndOfMonth());
    }


    @Transactional
    public List<Ticket> getTickets(Map<String, String> parameters) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate begindate = LocalDate.parse(parameters.get("begindate"), dateFormatter);
        LocalDate enddate = LocalDate.parse(parameters.get("enddate"), dateFormatter);

        List<Ticket> foundInRange = ticketRepository.getByDeadlineBetween(begindate, enddate);
        if(parameters.containsKey("project")) {
            return foundInRange.stream().filter(t -> t.getProject().getName().equalsIgnoreCase(parameters.get("project"))).collect(Collectors.toList());
        }

        return foundInRange;
    }

}
