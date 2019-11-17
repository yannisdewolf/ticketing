package be.dewolf.controller.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private String userName;
    private Long userId;

}
