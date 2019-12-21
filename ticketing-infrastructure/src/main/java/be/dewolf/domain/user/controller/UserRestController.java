package be.dewolf.domain.user.controller;

import be.dewolf.domain.common.ValueDto;
import be.dewolf.domain.user.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ValueDto> getAllUsers() {
        return userService.findAll()
                          .stream()
                          .map(uv -> ValueDto.builder()
                                             .id(uv.getId())
                                             .name(uv.getName())
                                             .build())
                          .collect(Collectors.toList());
    }

}
