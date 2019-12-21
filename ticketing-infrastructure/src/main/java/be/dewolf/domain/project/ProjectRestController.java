package be.dewolf.domain.project;

import be.dewolf.domain.common.ValueDto;
import be.dewolf.domain.user.Group;
import be.dewolf.domain.user.GroupRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/project")
public class ProjectRestController {

    private GroupRepository groupRepository;
    private ProjectRepository projectRepository;

    public ProjectRestController(GroupRepository groupRepository, ProjectRepository projectRepository) {
        this.groupRepository = groupRepository;
        this.projectRepository = projectRepository;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ValueDto> allProjects() {
        return this.projectRepository.findAll().stream().map(this::toValueDto).collect(Collectors.toList());
    }

    private ValueDto toValueDto(Project group) {
        return ValueDto.builder()
                       .id(group.getId())
                       .name(group.getName())
                       .build();
    }

}
