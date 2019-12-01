package be.dewolf.domain.task;

import com.sun.source.util.TaskListener;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Tasklist, Long> {

    @Query("select t from Tasklist t " +
            "join fetch t.sequences s " +
            "join fetch s.task " +
            "where t.id= ?1")
    Tasklist getListWithTasks(Long id);

}
