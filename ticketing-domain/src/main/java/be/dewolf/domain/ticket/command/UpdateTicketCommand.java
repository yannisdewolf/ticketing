package be.dewolf.domain.ticket.command;

import be.dewolf.domain.ticket.Priority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTicketCommand {

    private Long id;

    private LocalDate deadline;
    private String description;
    private Long assignedUserId;
    private Long assignedGroupId;
    private Priority priority;

}
