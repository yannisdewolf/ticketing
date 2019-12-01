package be.dewolf.domain.task;

import be.dewolf.domain.task.command.AddTaskToTasklistCommand;
import be.dewolf.domain.task.command.CreateTasklistCommand;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTaskToTaskList(AddTaskToTasklistCommand command) {

    }


    public void createTasklist(CreateTasklistCommand command) {

    }

}
