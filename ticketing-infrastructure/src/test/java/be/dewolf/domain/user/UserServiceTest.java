package be.dewolf.domain.user;

import be.dewolf.domain.user.command.CreateUserCommand;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(properties = "")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupRepository groupRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void createUser() {
        Group s = new Group();
        s.setName("developers");
        groupRepository.save(s);
        userService.createUser(new CreateUserCommand("yannis", "de wolf", "mailadres", "developers"));

        List<User> all = userService.findAll();
        Assertions.assertThat(all).hasSize(1);

        //List<User> select_q_from_user_q = em.createQuery("select q from User q join fetch q.groups ", User.class)
        //                                    .getResultList();


        //System.out.println(select_q_from_user_q);

    }

    //@Configuration
    //@EnableJpaRepositories(basePackages = "be.dewolf.domain.user")
    //@ComponentScan(basePackages = "be.dewolf.domain.user")
    //static class TestConfiguration {
    //}
}
