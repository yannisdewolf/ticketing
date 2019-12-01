package be.dewolf.domain.task;

import org.hibernate.jpa.QueryHints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;
    @PersistenceContext
    private EntityManager em;

    @Test
    public void createTasklist() {
        Tasklist tasklist = new Tasklist();
        tasklist.setListType("flyer1");

        taskRepository.save(tasklist);

        List<Tasklist> byEMFound = em.createQuery("select q from Tasklist q", Tasklist.class)
                                     .getResultList();

        assertThat(byEMFound).hasSize(1);

        assertThat(byEMFound.get(0).getSequences()).isEmpty();

    }

    @Test
    public void createListWithTasks() {
        Tasklist tasklist = new Tasklist();

        Task firstTask = new Task();
        firstTask.setTitle("open computer");
        firstTask.setDescription("push the power button");

        Task secondTask = new Task();
        secondTask.setTitle("contact designer");
        secondTask.setDescription("call the designer to ask for a design to mail");
        tasklist.addTask(1, firstTask);
        tasklist.addTask(2, secondTask);


        Tasklist save = taskRepository.save(tasklist);


        em.flush();
        em.clear();

        Tasklist tasklist1 = taskRepository.getListWithTasks(save.getId());

        assertThat(tasklist1).isNotNull();
        assertThat(tasklist1.getSequences()).hasSize(2);
        assertThat(tasklist1.getSequences().stream()
                            .filter(seq -> {
                                Task task = seq.getTask();
                                String title = task.getTitle();
                                return title.equalsIgnoreCase("open computer") && seq.getSequence() == 1;
                            })
                            .count()
        ).isEqualTo(1);


        assertThat(tasklist1.getSequences().stream()
                            .filter(seq -> seq.getTask().getTitle().equalsIgnoreCase("contact designer") && seq.getSequence() == 2)
                            .count()
        ).isEqualTo(1);
    }

    @Test
    public void addTaskToExistingList() {
        Tasklist tasklist = new Tasklist();

        Tasklist savedList = taskRepository.save(tasklist);

        tasklist = taskRepository.getOne(savedList.getId());

        Task firstTask = new Task();
        firstTask.setTitle("open computer");
        firstTask.setDescription("push the power button");

        Task secondTask = new Task();
        secondTask.setTitle("contact designer");
        secondTask.setDescription("call the designer to ask for a design to mail");
        tasklist.addTask(1, firstTask);
        tasklist.addTask(2, secondTask);


        Tasklist save = taskRepository.save(tasklist);


        em.flush();
        em.clear();

        Tasklist tasklist1 = taskRepository.getListWithTasks(save.getId());

        assertThat(tasklist1).isNotNull();
        assertThat(tasklist1.getSequences()).hasSize(2);
        assertThat(tasklist1.getSequences().stream()
                            .filter(seq -> {
                                Task task = seq.getTask();
                                String title = task.getTitle();
                                return title.equalsIgnoreCase("open computer") && seq.getSequence() == 1;
                            })
                   .count()
        ).isEqualTo(1);


        assertThat(tasklist1.getSequences().stream()
                            .filter(seq -> seq.getTask().getTitle().equalsIgnoreCase("contact designer") && seq.getSequence() == 2)
                            .count()
        ).isEqualTo(1);


    }

}
