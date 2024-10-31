package com.example.x_leagues.web.vm.auth;


import com.example.x_leagues.model.enums.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class ResponseVM {

    private UUID id;
    private String username;
    private String email;
    private Role role;
    private String redirect;
}
