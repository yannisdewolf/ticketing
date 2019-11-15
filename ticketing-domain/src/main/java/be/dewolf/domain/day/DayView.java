package be.dewolf.domain.day;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class DayView {

    private LocalDate date;
    private List<TicketInfo> ticketInfo;

}
