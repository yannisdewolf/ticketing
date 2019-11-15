package be.dewolf.domain.day;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DayService {

    public static final String PROJECT_1 = "project1";
    private static final String PROJECT_2 = "project2";

    public List<DayView> getDayViews() {
        Map<LocalDate, List<TicketInfo>> collect = Stream.of(
                TicketInfo.builder()
                          .assignee("yannis")
                          .description("omschrijving11")
                          .deadline(LocalDate.of(2019, 11, 1))
                          .project(PROJECT_1)
                          .build(),

                TicketInfo.builder()
                          .assignee("yannis")
                          .description("omschrijving13")
                          .deadline(LocalDate.of(2019, 11, 3))
                          .project(PROJECT_1)
                          .build(),

                TicketInfo.builder()
                          .assignee("johnn")
                          .description("omschrijving115")
                          .deadline(LocalDate.of(2019, 11, 15))
                          .project(PROJECT_1)
                          .build(),

                TicketInfo.builder()
                          .assignee("tiny")
                          .description("omschrijving227")
                          .deadline(LocalDate.of(2019, 11, 27))
                          .project(PROJECT_2)
                          .build(),

                TicketInfo.builder()
                          .assignee("yannis")
                          .description("omschrijving25")
                          .deadline(LocalDate.of(2019, 11, 5))
                          .project(PROJECT_2)
                          .build())
                                                         .collect(Collectors.groupingBy(TicketInfo::getDeadline));

        return collect.entrySet().stream()
               .map(entry -> new DayView(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

    }

}
