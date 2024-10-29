package com.example.x_leagues.repository;

import com.example.x_leagues.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<AppUser> findByUsernameOrEmail(String username, String email);
}
