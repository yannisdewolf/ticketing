package be.dewolf.domain.ticket;

import be.dewolf.domain.ticket.command.CreateTicketCommand;
import be.dewolf.domain.user.*;
import be.dewolf.domain.user.command.CreateUserCommand;
import org.hibernate.jpa.QueryHints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TicketServiceTest {

    @Autowired
    private TicketService ticketService;

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

        CreateUserCommand ucmd = new CreateUserCommand("yannis", "de wolf", "mail", null);
        User createdUser = userService.createUser(ucmd);

        CreateTicketCommand cmd = new CreateTicketCommand(LocalDate.now()
                                                                   .plusDays(10), "some task", createdUser.getId(), null, "comment", Priority.MEDIUM);
        Ticket persistedTicket = ticketService.createTicket(cmd);

        //em.flush();
        em.clear();

        Ticket foundTicket = em.createQuery("select distinct t from Ticket t " +
                                                    "join fetch t.comments " +
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
