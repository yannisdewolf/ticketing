package be.dewolf.domain.task;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "task_sequence")
@Data
public class TaskSequence implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST},fetch = FetchType.EAGER)
    private Task task;

    @ManyToOne
    private Tasklist tasklist;

    private int sequence;
}
