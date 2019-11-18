package be.dewolf.domain.user.controller;

import be.dewolf.domain.common.ValueDto;
import be.dewolf.domain.user.Group;
import be.dewolf.domain.user.GroupRepository;
import be.dewolf.domain.user.UserService;
import be.dewolf.domain.user.command.CreateUserCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private GroupRepository groupRepository;

    public UserController(UserService userService, GroupRepository groupRepository) {
        this.userService = userService;
        this.groupRepository = groupRepository;
    }

    @GetMapping("/all")
    public ModelAndView getAllUsers() {
        return new ModelAndView("components/user/all", "users", userService.findAll());
    }

    @GetMapping("/create")
    public ModelAndView createUser() {
        ModelAndView user = new ModelAndView("components/user/newUser", "user", new CreateUserCommand());
        user.addObject("groups",this.groupRepository.findAll().stream().map(this::toValueDto).collect(Collectors.toList()));
        return user;
    }

    @PostMapping("/create")
    public ModelAndView createUser(@ModelAttribute CreateUserCommand createUserCommand) {
        userService.createUser(createUserCommand);
        return new ModelAndView("redirect:/user/all");
    }

    private ValueDto toValueDto(Group group) {
        return ValueDto.builder()
                       .id(group.getId())
                       .name(group.getName())
                       .build();
    }

}
