package com.example.x_leagues.services.dto;


import com.example.x_leagues.annotations.UniqueEmail;
import com.example.x_leagues.annotations.UniqueUsername;
import com.example.x_leagues.model.enums.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AppUserDTO {

    private UUID id;
    private String username;
    private String email;
    private Role role;

}
