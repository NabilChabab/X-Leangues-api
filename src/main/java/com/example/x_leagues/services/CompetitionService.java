package com.example.x_leagues.services;

import com.example.x_leagues.model.Competition;
import org.springframework.data.domain.Page;


import org.springframework.data.domain.Pageable;

public interface CompetitionService {
    Competition save(Competition competition);

    Page<Competition> findAll(Pageable pageable);
}
