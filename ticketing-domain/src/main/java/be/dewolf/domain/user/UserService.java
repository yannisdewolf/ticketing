package be.dewolf.domain.user;

import be.dewolf.domain.user.command.CreateUserCommand;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private GroupRepository groupRepository;
    private UserRepository userRepository;

    public UserService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserCommand userCommand) {
        User user = new User();
        user.setEmail(userCommand.getEmail());
        user.setFirstName(userCommand.getFirstName());
        user.setLastName(userCommand.getLastName());
        userRepository.save(user);


        if(userCommand.getGroup() != null) {
            Group foundGroup = groupRepository.findByName(userCommand.getGroup());
            user.addGroup(foundGroup);
        }

        return userRepository.save(user);

        //foundGroup.addUser(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
