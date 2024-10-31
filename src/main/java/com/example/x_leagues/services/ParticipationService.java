package com.example.x_leagues.services;

import com.example.x_leagues.model.Participation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParticipationService {


    Participation save(Participation participation);
    Page<Participation> findAll(Pageable pageable);
}
