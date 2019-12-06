package be.dewolf.domain.project;

import be.dewolf.domain.common.ValueDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String color;

    public ValueDto toDto() {
        return ValueDto.builder()
                       .id(getId())
                       .name(getName())
                       .build();
    }

}
