package com.example.x_leagues.web.vm.auth;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginVm {


    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
