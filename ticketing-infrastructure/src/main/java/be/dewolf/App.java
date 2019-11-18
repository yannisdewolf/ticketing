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

    //@Bean
    ApplicationRunner applicationRunner(
            ProjectRepository projectRepository,
            UserService userService,
            GroupRepository groupRepository
    ) {
        return args -> {
            Stream.of("developers", "dba", "marketing", "finance", "hr")
                  .forEach(g -> {
                      Group group = new Group();
                      group.setName(g);
                      groupRepository.save(group);
                  });

            Stream.of(Arrays.asList("ednamode@mail.be", "Edna", "Mode", "developers"),
                      Arrays.asList("RandleMcMurphy@mail.be", "Randle", "Mc Murphy", "developers"),
                      Arrays.asList("normanbates@mail.be", "Norman", "Bates", "developers"),
                      Arrays.asList("maximus@mail.be", "maximus", "Gladiator", "dba", "developers", "marketing", "finance", "hr"),
                      Arrays.asList("grommit@mail.be", "Grommit", "Dog", "dba"),
                      Arrays.asList("jamesbond@mail.be", "James", "Bond", "marketing"),
                      Arrays.asList("hansolo@mail.be", "Han", "Solo", "finance"),
                      Arrays.asList("indianajones@mail.be", "Indiana", "Jones", "hr"))
                  .forEach(userDataList -> {
                      String email = userDataList.get(0);
                      String firstname = userDataList.get(1);
                      String lastname = userDataList.get(2);
                      String firstGroup = userDataList.get(3);
                      User createdUser = userService.createUser(new CreateUserCommand(firstname, lastname, email, firstGroup));


                      if (userDataList.size() > 4) {
                          userDataList.subList(4, userDataList.size() - 1)
                                      .forEach(group -> userService.addGroupToUser(group, createdUser.getId()));

                      }
                  });

            Stream.of("flyer1", "flyer2")
                  .forEach(project -> {
                      Project project1 = new Project();
                      project1.setName(project);

                      projectRepository.save(project1);
                  });

        };
    }

}
