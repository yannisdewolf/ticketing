package be.dewolf.domain.user;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Test
    public void testPersist() {
        Group s = new Group();
        s.setName("developers");
        Group savedGroup = groupRepository.save(s);

        User user = new User();
        user.setLastName("de wolf");
        user.setFirstName("yannis");
        user.setEmail("email");

        userRepository.save(user);
        user.addGroup(savedGroup);

        userRepository.save(user);

        assertThat(userRepository.findAll()).hasSize(1);
        assertThat(groupRepository.findAll()).hasSize(1);
    }

    @Configuration
    @ComponentScan(basePackages = "be.dewolf.domain.user")
    static class TestConfiguration {
    }

}
