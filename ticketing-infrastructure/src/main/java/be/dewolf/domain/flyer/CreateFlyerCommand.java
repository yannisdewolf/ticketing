package be.dewolf.domain.flyer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFlyerCommand {

    private long projectId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startFlyer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate aanleverenPOSDrukker;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate aanleverenFolderDrukker;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate kickOffPosPreviousFlyer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate commercieleMeeting;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate kickOff1FeedbackTom;


}
