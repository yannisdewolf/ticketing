package be.dewolf.domain.ticket.controller;

import be.dewolf.domain.ticket.TicketService;
import be.dewolf.domain.ticket.command.CreateTicketCommand;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ticket")

public class TicketRestController {

    private TicketService ticketService;

    public TicketRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/message")
    public String message() {
        return "hello world";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    //@CrossOrigin(origins = "http://localhost:4200")
    public void createTicket(
            @Valid @RequestBody  CreateTicketCommand createTicketCommand
    ) {

        this.ticketService.createTicket(createTicketCommand);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleValidationException(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                             .body("something went wrong " + ex.getMessage());
    }

}
