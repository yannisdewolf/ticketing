package be.dewolf.domain.day;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class TicketInfo {

    private LocalDate deadline;
    private String project;

    private String assignee;
    private String title;

}
