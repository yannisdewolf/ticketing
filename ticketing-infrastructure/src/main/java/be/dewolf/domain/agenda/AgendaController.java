package be.dewolf.domain.agenda;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/agenda")
public class AgendaController {

    @GetMapping
    public ModelAndView getTickets() {

        List<CalendarItem> calendarItems = Arrays.asList(
                new CalendarItem("2019-11-01T00:00:00Z", "allerheiligen"),
                new CalendarItem("2019-11-02T00:00:00Z", "allerzielen"),
                new CalendarItem("2019-11-11T00:00:00Z", "wapenstilstand"),
                new CalendarItem("2019-12-06T00:00:00Z", "sinterklaas"),
                new CalendarItem("2019-12-25T00:00:00Z", "kerstmis")
        );

        ModelAndView modelAndView = new ModelAndView("components/agenda/agenda", "begroeting", "hello world!");
        modelAndView.addObject("calendarItems", calendarItems);
        return modelAndView;
    }

}
