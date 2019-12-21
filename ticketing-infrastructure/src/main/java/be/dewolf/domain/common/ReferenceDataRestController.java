package be.dewolf.domain.common;

import be.dewolf.domain.ticket.Priority;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/reference")
public class ReferenceDataRestController {

    @GetMapping(value = "/priorities", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getAllPriorities() {
        return Stream.of(Priority.values())
                     .map(Priority::name)
                     .collect(Collectors.toList());
    }

}
