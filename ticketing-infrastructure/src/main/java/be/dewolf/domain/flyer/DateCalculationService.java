package be.dewolf.domain.flyer;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

@Service
public class DateCalculationService {

    private static final Set<DayOfWeek> WEEKEND = Collections.unmodifiableSet(EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY));
    private static final Set<LocalDate> HOLIDAYS = Collections.unmodifiableSet(Set.of(
            LocalDate.of(2019, 12, 25),
            LocalDate.of(2020,1,1),
            LocalDate.of(2020,4,13),
            LocalDate.of(2020,5,1),
            LocalDate.of(2020,5,21),
            LocalDate.of(2020,6,1),
            LocalDate.of(2020,7,21),
            LocalDate.of(2020,8,15),
            LocalDate.of(2020,11,1),
            LocalDate.of(2020,11,11),
            LocalDate.of(2020,12,25)
    ));

    public LocalDate getDaysDifferentFromTakingWeekendsAndHolidaysIntoAccount(
            LocalDate startDate,
            int daysDifference
    ) {

        LocalDate d = startDate;
        for(int i=0; i<Math.abs(daysDifference); i++) {
            if(daysDifference < 0) {
                d = getPreviousWorkingDay(d);
            }else {
                d = getNextWorkingDay(d);
            }
        }

        return d;
    }

    private LocalDate getPreviousWorkingDay(LocalDate date) {
        LocalDate nextDay = date.minusDays(1);
        while(WEEKEND.contains(nextDay.getDayOfWeek()) || HOLIDAYS.contains(nextDay)) {
            nextDay = nextDay.minusDays(1);
        }
        return nextDay;
    }

    private LocalDate getNextWorkingDay(LocalDate date) {
        LocalDate nextDay = date.plusDays(1);
        while(WEEKEND.contains(nextDay.getDayOfWeek()) || HOLIDAYS.contains(nextDay)) {
            nextDay = nextDay.plusDays(1);
        }
        return nextDay;
    }

}
