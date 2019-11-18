package be.dewolf.domain.ticket;

import be.dewolf.domain.project.Project;
import be.dewolf.domain.project.ProjectRepository;
import be.dewolf.domain.ticket.command.CreateTicketCommand;
import be.dewolf.domain.user.Group;
import be.dewolf.domain.user.GroupRepository;
import be.dewolf.domain.user.User;
import be.dewolf.domain.user.UserService;
import be.dewolf.domain.user.command.CreateUserCommand;
import org.hibernate.jpa.QueryHints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql("/test-data.sql")
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserService userService;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void createTicket() {

        Group s = new Group();
        s.setName("group1");
        groupRepository.save(s);

        Project project1 = new Project();
        projectRepository.save(project1);

        CreateUserCommand ucmd = new CreateUserCommand("yannis", "de wolf", "mail", null);
        User createdUser = userService.createUser(ucmd);

        CreateTicketCommand cmd = new CreateTicketCommand("title",
                                                          "description", LocalDate.now()
                                                                                  .plusDays(10), createdUser.getId(), null, project1.getId(), Priority.MEDIUM);
        Ticket persistedTicket = ticketService.createTicket(cmd);

        //em.flush();
        //em.clear();

        Ticket foundTicket = em.createQuery("select distinct t from Ticket t " +
                                                    "left join fetch t.comments " +
                                                    "join fetch t.user " +
                                                    "where t.id = :id" +
                                                    "", Ticket.class)
                               .setParameter("id", persistedTicket.getId())
                               .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                               .getSingleResult();

        assertThat(foundTicket).isNotNull();

        System.out.println(foundTicket);
    }

}
