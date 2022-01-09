package com.marscolony.bankacc.cmd.api.controllers;

import com.marscolony.bankacc.cmd.api.commands.DepositFundsCommand;
import com.marscolony.bankacc.cmd.api.dto.OpenAccountResponse;
import com.marscolony.bankacc.core.api.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/depositFunds")
public class DepositFundsController {

    private final CommandGateway commandGateway;

    @Autowired
    public DepositFundsController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<BaseResponse> depositFunds(@PathVariable(value = "id") String id,
                                                     @Valid @RequestBody DepositFundsCommand command) {

        try {
            command.setId(id);
            commandGateway.sendAndWait(command);
            return new ResponseEntity<>(new BaseResponse("successfully opened a new bank account"), HttpStatus.CREATED);
        } catch (Exception e) {
            var errorMessage = "Error while processing request to deposit funds for id " + id;
            System.out.println(e.toString());
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
