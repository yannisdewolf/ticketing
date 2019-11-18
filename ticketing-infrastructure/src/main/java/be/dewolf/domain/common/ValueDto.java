package be.dewolf.domain.common;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ValueDto {
    private Long id;
    private String name;
}
