package be.dewolf.domain.day;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class DayService {

    public static final String PROJECT_1 = "project1";
    private static final String PROJECT_2 = "project2";

    public List<DayView> getDayViews() {

        List<TicketInfo> allTicketInfos = Arrays.asList(
                TicketInfo.builder()
                          .assignee("yannis")
                          .title("omschrijving11")
                          .deadline(LocalDate.of(2019, 11, 1))
                          .project(PROJECT_1)
                          .build(),
                TicketInfo.builder()
                          .assignee("tiny")
                          .title("omschrijving21")
                          .deadline(LocalDate.of(2019, 11, 1))
                          .project(PROJECT_2)
                          .build(),
                TicketInfo.builder()
                          .assignee("yannis")
                          .title("omschrijving13")
                          .deadline(LocalDate.of(2019, 11, 3))
                          .project(PROJECT_1)
                          .build(),

                TicketInfo.builder()
                          .assignee("johnn")
                          .title("omschrijving115")
                          .deadline(LocalDate.of(2019, 11, 15))
                          .project(PROJECT_1)
                          .build(),

                TicketInfo.builder()
                          .assignee("tiny")
                          .title("omschrijving227")
                          .deadline(LocalDate.of(2019, 11, 27))
                          .project(PROJECT_2)
                          .build(),

                TicketInfo.builder()
                          .assignee("yannis")
                          .title("omschrijving25")
                          .deadline(LocalDate.of(2019, 11, 5))
                          .project(PROJECT_2)
                          .build());

        return CalendarView.from(allTicketInfos).getDayViews();

    }

}
