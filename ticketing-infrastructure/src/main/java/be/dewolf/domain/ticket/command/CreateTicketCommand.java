package be.dewolf.domain.ticket.command;

import be.dewolf.domain.ticket.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketCommand {

    private String title;
    private String description;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate deadline;
    private Long assignedUserId;
    private Long assignedGroupId;
    private Long assignedProjectId;
    private Priority priority;

}
