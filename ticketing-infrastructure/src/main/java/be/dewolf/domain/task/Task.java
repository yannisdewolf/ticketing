package be.dewolf.domain.task;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String title;

    @OneToOne
    private TaskSequence taskSequence;

}
