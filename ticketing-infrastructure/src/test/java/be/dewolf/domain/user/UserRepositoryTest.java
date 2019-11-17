package be.dewolf.domain.user;

import org.hibernate.jpa.QueryHints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
//@SpringBootTest
@DataJpaTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testPersist() {
        Group s = new Group();
        s.setName("qwerty");
        Group savedGroup = groupRepository.save(s);

        User user = new User();
        user.setLastName("random");
        user.setFirstName("data");
        user.setEmail("email");

        userRepository.save(user);
        user.addGroup(savedGroup);

        userRepository.save(user);

        List<User> foundByEM = em.createQuery("select p " +
                                             "from User p " +
                                             "join fetch p.groups " +
                                             "where p.id = :userId", User.class)
                        .setParameter("userId", user.getId())
                        .getResultList();

        assertThat(foundByEM.get(0).getGroups())
                .hasSize(1)
        .extracting("name").containsOnly("qwerty");

        assertThat(userRepository.findById(user.getId())).isNotNull();
        assertThat(groupRepository.findByName("qwerty")).isNotNull();
    }

    @Test
    public void getUsers() {
        List<User> list = em.createQuery("select distinct u " +
                                                 "from User u " +
                                                 "join fetch u.groups", User.class)
                            .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                                            .getResultList();
        System.out.println(
                list.stream()
                    .map(u -> u.toString() + " (" + u.getGroups().stream().map(Group::toString).collect(Collectors.joining(",")) + ")")
                    .collect(Collectors.joining("\r\n")));
    }

    @Test
    public void initializedData() {

        List<User> userWithMultipleGroups = em.createQuery("select distinct u " +
                                                       "from User u " +
                                                       "join fetch u.groups " +
                                                       "where u.firstName = :firstName", User.class)
                                  .setParameter("firstName", "maximus")
                                              .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                                  .getResultList();

        System.out.println(userWithMultipleGroups);



        assertThat(userWithMultipleGroups).hasSize(1);
        assertThat(userWithMultipleGroups.get(0).getGroups())
                                          .extracting("name")
                                          .containsOnly("developers",
                                                        "dba",
                                                        "marketing",
                                                        "finance",
                                                        "hr");
    }

}
