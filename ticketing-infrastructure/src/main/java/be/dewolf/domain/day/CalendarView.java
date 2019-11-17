package be.dewolf.domain.day;

import lombok.Value;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

public class CalendarView {

    private Map<LocalDate, ProjectTodoList> projectTodoListPerDate;

    private CalendarView() {
        projectTodoListPerDate = new HashMap<>();
    }

    public static CalendarView from(List<TicketInfo> allTicketInfos) {
        CalendarView calendarView = new CalendarView();
        allTicketInfos
                .forEach(ti -> calendarView.addTicketToProject(ti.getDeadline(), ti.getProject(), ti));

        return calendarView;
    }

    private void addTicketToProject(LocalDate date, String project, TicketInfo ticketInfo) {
        if (projectTodoListPerDate.get(date) == null) {
            projectTodoListPerDate.put(date, new ProjectTodoList());
        }

        projectTodoListPerDate.get(date)
                              .addForProject(project, ticketInfo);

    }

    public List<DayView> getDayViews() {

        LocalDate firstDayToDisplay = LocalDate.of(2019, 11, 1);
        firstDayToDisplay
                 .datesUntil(firstDayToDisplay.with(TemporalAdjusters.firstDayOfNextMonth()))
                 .forEach(d -> projectTodoListPerDate.putIfAbsent(d, new ProjectTodoList()));

        return projectTodoListPerDate.entrySet()
                                     .stream()
                                     .map(entry -> new DayView(entry.getKey(), entry.getValue()
                                                                                    .toTicketsPerProject()))
                                     .sorted(Comparator.comparing(DayView::getDate))
                                     .collect(Collectors.toList());
    }

    @Value
    static class ProjectTodoList {

        private Map<String, TicketsTodo> ticketsTodoPerProject;

        private ProjectTodoList() {
            this.ticketsTodoPerProject = new HashMap<>();
        }

        public void addForProject(String project, TicketInfo ticketInfo) {
            ticketsTodoPerProject.putIfAbsent(project, new TicketsTodo());
            ticketsTodoPerProject.get(project)
                                 .addTicketInfo(ticketInfo);
        }

        public Map<String, List<TicketInfo>> toTicketsPerProject() {
            return ticketsTodoPerProject.entrySet()
                                        .stream()
                                        .collect(Collectors.toMap(e -> e.getKey(),
                                                                  e -> new ArrayList<>(e.getValue()
                                                                                        .getTicketsTodo())));
        }
    }


    @Value
    static class TicketsTodo {
        Set<TicketInfo> ticketsTodo;

        private TicketsTodo() {
            this.ticketsTodo = new HashSet<>();
        }

        public void addTicketInfo(TicketInfo ticketInfo) {
            ticketsTodo.add(ticketInfo);
        }
    }

}
