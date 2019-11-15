package be.dewolf.controller.ticket;

import be.dewolf.domain.ticket.Priority;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Value
public class TicketDTO {

    private String assignedUser;
    private String description;
    private LocalDate deadLine;
    private Priority priority;

    private String project;


}
