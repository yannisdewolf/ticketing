package be.dewolf.domain.project;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

}
