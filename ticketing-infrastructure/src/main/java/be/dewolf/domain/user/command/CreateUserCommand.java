package be.dewolf.domain.user.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserCommand {

    private String firstName, lastName, email;
    private Long group;

}
