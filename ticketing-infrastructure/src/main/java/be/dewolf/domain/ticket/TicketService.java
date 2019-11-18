package be.dewolf.domain.ticket;

import be.dewolf.domain.project.ProjectRepository;
import be.dewolf.domain.ticket.command.CreateTicketCommand;
import be.dewolf.domain.user.GroupRepository;
import be.dewolf.domain.user.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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
        if(createTicketCommand.getAssignedGroupId() != null) {
            builder.assignedGroup(groupRepository.getOne(createTicketCommand.getAssignedGroupId()));
        }
        if(createTicketCommand.getAssignedUserId() != null) {
            builder.user(userRepository.getOne(createTicketCommand.getAssignedUserId()));
        }

        builder.project(projectRepository.getOne(createTicketCommand.getAssignedProjectId()));
        builder.title(createTicketCommand.getTitle());
        builder.description(createTicketCommand.getDescription());
        builder.deadline(createTicketCommand.getDeadline());
        builder.priority(createTicketCommand.getPriority());
        return ticketRepository.save(builder.build());

    }

    public List<Ticket> getTickets() {
        return ticketRepository.findAll();
    }


}
