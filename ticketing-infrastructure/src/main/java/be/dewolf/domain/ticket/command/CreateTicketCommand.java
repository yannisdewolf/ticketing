package be.dewolf.domain.ticket.command;

import be.dewolf.domain.common.CustomDateDeserializer;
import be.dewolf.domain.ticket.Priority;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTicketCommand {

    @NotBlank
    private String title;
    private String description;
    @NotNull
    @JsonDeserialize(using = CustomDateDeserializer.class)
    //@DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate deadline;
    @JsonProperty("assignedUser")
    private Long assignedUserId;
    @JsonProperty("group")
    private Long assignedGroupId;
    @JsonProperty("project")
    private Long assignedProjectId;
    @NotNull
    private Priority priority;

}
