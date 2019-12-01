package be.dewolf.domain.task;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasklist")
@Data
public class Tasklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "list_type")
    private String listType;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "tasklist", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TaskSequence> sequences = new ArrayList<>();

    public void addTask(int sequence, Task task) {
        TaskSequence taskSequence = new TaskSequence();
        taskSequence.setSequence(sequence);
        taskSequence.setTask(task);
        taskSequence.setTasklist(this);
        sequences.add(taskSequence);
    }

}
