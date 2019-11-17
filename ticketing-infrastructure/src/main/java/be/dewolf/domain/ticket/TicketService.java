package be.dewolf.domain.ticket;

import be.dewolf.domain.ticket.command.CreateTicketCommand;
import be.dewolf.domain.ticket.command.UpdateTicketCommand;
import be.dewolf.domain.user.GroupRepository;
import be.dewolf.domain.user.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class TicketService {

    private TicketRepository ticketRepository;
    private GroupRepository groupRepository;
    private UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public void updateTicket(UpdateTicketCommand updateTicketCommand) {
        Ticket ticket = ticketRepository.getOne(updateTicketCommand.getId());

        Ticket newTicket = new Ticket();
        if(updateTicketCommand.getAssignedUserId() != null) {
            newTicket.setUser(userRepository.getOne(updateTicketCommand.getAssignedUserId()));
        }
        if(updateTicketCommand.getAssignedGroupId() != null) {
            newTicket.setAssignedGroup(groupRepository.getOne(updateTicketCommand.getAssignedGroupId()));
        }

        newTicket.setDeadline(updateTicketCommand.getDeadline());
        newTicket.setDescription(updateTicketCommand.getDescription());
        newTicket.setPriority(updateTicketCommand.getPriority());
    }

    public Ticket createTicket(CreateTicketCommand createTicketCommand) {
        Ticket ticket = new Ticket();
        if(createTicketCommand.getAssignedGroupId() != null) {
            ticket.setAssignedGroup(groupRepository.getOne(createTicketCommand.getAssignedGroupId()));
        }
        if(createTicketCommand.getAssignedUserId() != null) {
            ticket.setUser(userRepository.getOne(createTicketCommand.getAssignedUserId()));
        }

        Comment comment = new Comment();
        comment.setText(createTicketCommand.getComment());
        ticket.addComment(comment);

        ticket.setDescription(createTicketCommand.getDescription());
        ticket.setDeadline(createTicketCommand.getDeadline());

        return ticketRepository.save(ticket);



    }

}
