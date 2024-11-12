package com.example.x_leagues.services;

import com.example.x_leagues.model.Participation;

import com.example.x_leagues.services.dto.CompetitionHistoryDTO;
import com.example.x_leagues.services.dto.CompetitionResultDTO;
import com.example.x_leagues.services.dto.PodiumDTO;
import com.example.x_leagues.services.dto.ScoreUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ParticipationService {


    Participation save(Participation participation);
    Page<Participation> findAll(Pageable pageable);
    List<CompetitionResultDTO> getUserCompetitionResults(UUID userId);
    List<PodiumDTO> getCompetitionPodium(UUID competitionId);
    Page<CompetitionHistoryDTO> getUserCompetitionHistory(UUID userId , Pageable pageable);
    double updateScore(Participation participation);
}
