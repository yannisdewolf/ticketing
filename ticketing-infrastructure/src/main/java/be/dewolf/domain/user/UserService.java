package be.dewolf.domain.user;

import be.dewolf.domain.user.command.CreateUserCommand;
import be.dewolf.domain.user.controller.UserView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private GroupRepository groupRepository;
    private UserRepository userRepository;

    public UserService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(CreateUserCommand userCommand) {
        User user = new User();
        user.setEmail(userCommand.getEmail());
        user.setFirstName(userCommand.getFirstName());
        user.setLastName(userCommand.getLastName());
        userRepository.save(user);


        if (userCommand.getGroup() != null) {
            Group foundGroup = groupRepository.getOne(userCommand.getGroup());
            user.addGroup(foundGroup);
        }

        return userRepository.save(user);

        //foundGroup.addUser(user);
    }

    @Transactional
    public void addGroupToUser(String role, Long user) {
        User one = this.userRepository.getOne(user);
        one.getGroups()
           .add(groupRepository.findByName(role));
        //one.addGroup();
        userRepository.save(one);
    }

    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    public List<UserView> findAll() {
        return userRepository.findAll()
                             .stream()
                             .map(u -> new UserView(u.getId(), u.getName(), u.getEmail(), u.getGroups()
                                                                                .stream()
                                                                                .map(Group::getName)
                                                                                .collect(Collectors.joining(","))))
                             .collect(Collectors.toList());
    }

}
