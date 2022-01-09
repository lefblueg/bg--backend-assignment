package com.marscolony.reviews.cmd.api.controllers;

import com.marscolony.reviews.cmd.api.commands.CreateReviewCommand;
import com.marscolony.reviews.cmd.api.dto.CreateReviewResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/createUnitReview")
public class CreateReviewController {
    private final CommandGateway commandGateway;

    @Autowired
    public CreateReviewController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<CreateReviewResponse> createReview(@Valid @RequestBody CreateReviewCommand command) {

        var id = UUID.randomUUID().toString();

        try {
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new CreateReviewResponse(id, "successfully done the review"), HttpStatus.CREATED);
        } catch (Exception e) {
            var safeError = "Error while processing request to create a review for id " + id;
            System.out.println(e.toString());
            return new ResponseEntity<>(new CreateReviewResponse(id, safeError), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
