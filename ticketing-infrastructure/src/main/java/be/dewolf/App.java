package be.dewolf;

import be.dewolf.domain.project.Project;
import be.dewolf.domain.project.ProjectRepository;
import be.dewolf.domain.user.Group;
import be.dewolf.domain.user.GroupRepository;
import be.dewolf.domain.user.User;
import be.dewolf.domain.user.UserService;
import be.dewolf.domain.user.command.CreateUserCommand;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Hello world!
 */
@SpringBootApplication
@Controller
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("redirect:/ticket/dayviews");
    }

}
