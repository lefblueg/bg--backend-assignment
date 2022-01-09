package com.marscolony.user.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Account {

    @Size(min = 2, message = "username must have at least two characters")
    private String userName;
    @Size(min = 7, message = "password must have at least seven characters")
    private String password;
    @NotNull(message = "specify at least one role")
    private List<Role> roles;

}
