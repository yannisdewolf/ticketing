package be.dewolf.domain.ticket.command;

import be.dewolf.domain.ticket.Priority;
import lombok.Value;

import java.time.LocalDate;

@Value
public class CreateTicketCommand {

    private LocalDate deadline;
    private String description;
    private Long assignedUserId;
    private Long assignedGroupId;
    private String comment;
    private Priority priority;

}
