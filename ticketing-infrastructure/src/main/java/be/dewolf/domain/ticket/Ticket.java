package be.dewolf.domain.ticket;

import be.dewolf.domain.project.Project;
import be.dewolf.domain.user.Group;
import be.dewolf.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tickets")
//@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate deadline;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_user")
    @Getter
    private User user;

    @ManyToOne
    @JoinColumn(name = "assigned_group")
    private Group assignedGroup;

    @ManyToOne
    private Project project;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ticket_id")
    @Builder.Default
    private Set<Comment> comments = new HashSet<>();

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    //public void addComment(Comment comment) {
    //    this.comments.add(comment);
    //}

}
