package be.dewolf.domain.user.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserView {

    private Long id;
    private String name;
    private String email;
    private String groups;

}
