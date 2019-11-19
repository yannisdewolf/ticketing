package be.dewolf.domain.agenda;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/agenda")
public class AgendaController {

    @GetMapping
    public ModelAndView getTickets() {
        return new ModelAndView("components/agenda/agenda");
    }

}
