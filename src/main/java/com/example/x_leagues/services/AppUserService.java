package com.example.x_leagues.services;

import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.services.dto.SearchDTO;
import com.example.x_leagues.services.dto.UserParticipationCountDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppUserService {
    @Transactional
    AppUser save(@Valid AppUser appUser);

    boolean checkPassword(String rawPassword, String encodedPassword);

    Optional<AppUser> findById(UUID id);

    AppUser login(String username, String password);

    Page<UserParticipationCountDTO> findAll(Pageable pageable);

    List<SearchDTO> searchMembers(SearchDTO searchDTO);
}
