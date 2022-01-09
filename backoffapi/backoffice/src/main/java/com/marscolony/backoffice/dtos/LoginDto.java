package com.marscolony.backoffice.dtos;

import lombok.Data;

@Data
public class LoginDto {
    private String usernameOrEmail;
    private String password;

}
