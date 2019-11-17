package be.dewolf.domain.user.command;

import lombok.Value;

@Value
public class CreateUserCommand {

    private final String firstName, lastName, email, group;

}
