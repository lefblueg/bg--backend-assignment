package com.marscolony.user.cmd.api.controllers;

import com.marscolony.user.cmd.api.commands.RemoveUserCommand;
import com.marscolony.user.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "")
public class RemoveUserController {

    private final CommandGateway commandGateway;

    @Autowired
    public RemoveUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRVILEGE')")
    public ResponseEntity<BaseResponse> removeUser(@PathVariable(value = "id") String id) {

        try {
            commandGateway.send(new RemoveUserCommand(id));
            return new ResponseEntity<>(new BaseResponse("User successfully removed!"), HttpStatus.OK);

        } catch (Exception e) {
            var errorMessage = "Error while processing remove user request for id: " + id;
            System.out.println(e.toString());
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
