package be.dewolf.domain.ticket;

import be.dewolf.domain.ticket.command.CreateTicketCommand;
import be.dewolf.domain.user.*;
import be.dewolf.domain.user.command.CreateUserCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
    private UserRepository userRepository;

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

        Ticket foundTicket = em.createQuery("select t from Ticket t " +
                                                    "join fetch t.comments " +
                                                    "join fetch t.user " +
                                                    "where t.id = :id" +
                                                    "", Ticket.class)
                               .setParameter("id", persistedTicket.getId())
                               .getSingleResult();

        assertThat(em.createQuery("from User u", User.class)
                     .getResultList()).hasSize(1);
        assertThat(em.createQuery("from Group u", Group.class)
                     .getResultList()).hasSize(1);
        assertThat(em.createQuery("from Comment u", Comment.class)
                     .getResultList()).hasSize(1);
        assertThat(em.createQuery("from Ticket u", Ticket.class)
                     .getResultList()).hasSize(1);

        assertThat(foundTicket).isNotNull();

        System.out.println(foundTicket);


    }

    @SpringBootApplication
    @EntityScan(basePackages = {"be.dewolf.domain"})
    @ComponentScan(basePackages = {"be.dewolf.domain"})
    static class TestConfiguration {
    }

}
