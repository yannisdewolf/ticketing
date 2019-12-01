package be.dewolf.domain.flyer;

import be.dewolf.domain.ticket.Priority;
import be.dewolf.domain.ticket.Ticket;
import be.dewolf.domain.ticket.command.CreateTicketCommand;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FlyerService {

    private DateCalculationService dateCalculationService;

    public FlyerService(DateCalculationService dateCalculationService) {
        this.dateCalculationService = dateCalculationService;

    }

    public List<CreateTicketCommand> createTickets(CreateFlyerCommand flyerCommand) {

        return Stream.of(
                        getTickets(flyerCommand::getStartFlyer, Tasks.START_FLYER, flyerCommand.getProjectId()),
                        getTickets(flyerCommand::getKickOffPosPreviousFlyer, Tasks.KO2_POS_PREV_FLYER, flyerCommand.getProjectId()),
                        getTickets(flyerCommand::getAanleverenFolderDrukker, Tasks.AANLEVEREN_FOLDER_DRUKKER, flyerCommand.getProjectId()),
                        getTickets(flyerCommand::getCommercieleMeeting, Tasks.COMMERCIELE_MEETING, flyerCommand.getProjectId()),
                        getTickets(flyerCommand::getKickOff1FeedbackTom, Tasks.KO1_FEEDBACKTOM, flyerCommand.getProjectId()),
                        getTickets(flyerCommand::getAanleverenPOSDrukker, Tasks.AANLEVEREN_POS_DRUKKER, flyerCommand.getProjectId()))
                     .flatMap(List::stream)
                     .collect(Collectors.toList());
    }

    private List<CreateTicketCommand> getTickets(Supplier<LocalDate> getStartFlyer, Tasks startFlyer, Long projectId) {
        List<CreateTicketCommand> collect = startFlyer.childTasks()
                                         .stream()
                                         .map(childTask -> {
                                             return createTicketCommandForTask(childTask, getStartFlyer.get(), projectId);
                                         })
                                         .collect(Collectors.toList());
        collect.add(createTicketCommandForTask(startFlyer, getStartFlyer.get(), projectId));
        return collect;
    }

    private CreateTicketCommand createTicketCommandForTask(Tasks childTask, LocalDate startFlyer, Long projectId) {
        return CreateTicketCommand.builder()
                           .deadline(dateCalculationService.getDaysDifferentFromTakingWeekendsAndHolidaysIntoAccount(startFlyer, childTask.days))
                           .title(childTask.name())
                           .assignedProjectId(projectId)
                           .priority(Priority.MEDIUM)
                           .assignedUserId(1L)
                           .build();


    }


    private Ticket createTicketForTask(Tasks childTask, LocalDate startFlyer) {
        return Ticket.builder()
                     .deadline(dateCalculationService.getDaysDifferentFromTakingWeekendsAndHolidaysIntoAccount(startFlyer, childTask.days))
                     .title(childTask.name())
                     .build();
    }

    enum Tasks {

        START_FLYER,
        CONTROLE_ONE_SHOT(0, START_FLYER),
        REPORTING(6, START_FLYER),
        MARKDOWN_VOORSTEL(8, START_FLYER),
        MARKDOWN_FINAL(10, START_FLYER),
        UPLOAD_MDO(-12, START_FLYER),

        AANLEVEREN_POS_DRUKKER,
        BESTELLIJST_POS(-1, AANLEVEREN_POS_DRUKKER),
        FINALE_CHECK_POS(-3, AANLEVEREN_POS_DRUKKER),
        POS_COMMUNICATIE_WINKELS(-4, AANLEVEREN_POS_DRUKKER),
        PRICETAGS(-4, AANLEVEREN_POS_DRUKKER),


        AANLEVEREN_FOLDER_DRUKKER,
        DEADLINE_MONTHLY_PACKAGE(3, AANLEVEREN_FOLDER_DRUKKER),
        POS_CREATIE(0, AANLEVEREN_FOLDER_DRUKKER),
        FINALE_VERSIE_FLYER_NL_FR(0, AANLEVEREN_FOLDER_DRUKKER),
        FINALE_VERSIE_FLYER_FR(-1, AANLEVEREN_FOLDER_DRUKKER),
        REVISIE_FRANSE_FLYER_ELAN(-3, AANLEVEREN_FOLDER_DRUKKER),
        FINALE_VERSIE_FLYER_NL(-4, AANLEVEREN_FOLDER_DRUKKER),
        REACTIE_VANUIT_CC(-5, AANLEVEREN_FOLDER_DRUKKER),
        FINALE_VERSIE_FLYER_DOORSTURENCC_STARTVERTALING(-7, AANLEVEREN_FOLDER_DRUKKER),// 3 taken?
        EINDE_CORRECTIES_DTP3(-8, AANLEVEREN_FOLDER_DRUKKER),
        CORRECTIE_DTP3(-9, AANLEVEREN_FOLDER_DRUKKER),
        DTP3(-10, AANLEVEREN_FOLDER_DRUKKER),
        EINDE_CORRECTIES_DTP2(-11, AANLEVEREN_FOLDER_DRUKKER),
        CORRECTIES_DTP2(-12, AANLEVEREN_FOLDER_DRUKKER),
        DTP2(-13, AANLEVEREN_FOLDER_DRUKKER),
        EINDE_CORRECTIES_DTP1(-14, AANLEVEREN_FOLDER_DRUKKER),
        DTP1(-15, AANLEVEREN_FOLDER_DRUKKER),
        BRIEF_AGENCY(-23, AANLEVEREN_FOLDER_DRUKKER),
        ONE_SHOTS_PRICE_SETTING(-26, AANLEVEREN_FOLDER_DRUKKER),
        FORECAST_FLYER_CC(-26, AANLEVEREN_FOLDER_DRUKKER),
        DRAFT2_GRID_MARGE(-40, AANLEVEREN_FOLDER_DRUKKER),
        FEEDBACK_CM(-43, AANLEVEREN_FOLDER_DRUKKER),
        DRAFT1_GRID_MARGE(-45, AANLEVEREN_FOLDER_DRUKKER),
        OPSTART_GRID_FOOD_NF_MATCH(-50, AANLEVEREN_FOLDER_DRUKKER),

        KO2_POS_PREV_FLYER,
        EXPORT_SLUIT_MARGE(0, KO2_POS_PREV_FLYER),


        COMMERCIELE_MEETING,
        FINALISATIE_NA_COMM_MEETING(2, COMMERCIELE_MEETING),
        PIVOT_COMMERCIELE_MEETING(-2, COMMERCIELE_MEETING),
        LAATSTE_AANPAAINGEN_FB_TOM(-3, COMMERCIELE_MEETING),


        KO1_FEEDBACKTOM;

        private final Tasks dt;
        private int days;

        Tasks() {
            this(0, null);
        }

        Tasks(int days, Tasks rootTask) {
            this.days = days;
            this.dt = rootTask;
        }

        public List<Tasks> childTasks() {
            return Stream.of(Tasks.values())
                         .filter(t -> t.hasRoot(this))
                         .collect(Collectors.toList());
        }

        boolean isRoot() {
            return dt == null;
        }

        boolean hasRoot(Tasks tasks) {
            return this.dt == tasks;
        }

    }

    static class ASDFASFASDFHandler {

    }

}
