package com.example.x_leagues.services;

import com.example.x_leagues.model.Competition;
import com.example.x_leagues.services.dto.CompetitionDetailsDTO;
import org.springframework.data.domain.Page;


import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CompetitionService {
    Competition save(Competition competition);

    Page<Competition> findAll(Pageable pageable);

    CompetitionDetailsDTO competitionDetails(UUID competitionId);
}
