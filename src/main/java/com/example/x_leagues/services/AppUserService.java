package com.example.x_leagues.services;

import com.example.x_leagues.model.AppUser;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface AppUserService {
    @Transactional
    AppUser save(@Valid AppUser appUser);

    boolean checkPassword(String rawPassword, String encodedPassword);

    AppUser findById(UUID id);

    AppUser login(String username, String password);
}
