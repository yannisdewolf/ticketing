package be.dewolf.domain.ticket.command;

import be.dewolf.domain.ticket.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketCommand {

    private LocalDate deadline;
    private String description;
    private Long assignedUserId;
    private Long assignedGroupId;
    private String comment;
    private Priority priority;

}
