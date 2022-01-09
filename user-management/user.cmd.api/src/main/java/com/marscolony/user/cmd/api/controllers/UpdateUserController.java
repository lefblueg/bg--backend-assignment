package com.marscolony.user.cmd.api.controllers;

import com.marscolony.user.cmd.api.commands.UpdateUserCommand;
import com.marscolony.user.core.dto.BaseResponse;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/updateUser")
public class UpdateUserController {

    private final CommandGateway commandGateway;

    public UpdateUserController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAuthority('WRITE_PRVILEGE')")
    public ResponseEntity<BaseResponse> updateUser(@PathVariable(value = "id") String id,
                                                   @Valid @RequestBody UpdateUserCommand command) {

        try {
            command.setId(id);
            commandGateway.send(command);

            return new ResponseEntity<>(new BaseResponse("User successfully updated!"), HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = "Error while processing update user request for id: " + id;
            System.out.println(e.toString());
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
