package be.dewolf.domain.day;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Value
public class DayView {

    private LocalDate date;

    //project-1: iets
    //project-2: iets anders
    private Map<String, List<TicketInfo>> todoPerProject;

}
