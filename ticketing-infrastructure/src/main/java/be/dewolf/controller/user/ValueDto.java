package be.dewolf.controller.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValueDto {

    private String name;
    private Long id;

}
