package com.example.x_leagues.services.impl;

import com.example.x_leagues.model.Participation;
import com.example.x_leagues.repository.ParticipationRepository;
import com.example.x_leagues.services.ParticipationService;
import com.example.x_leagues.services.dto.CompetitionResultDTO;
import com.example.x_leagues.services.dto.ScoreUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;

    public ParticipationServiceImpl(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }

    @Transactional
    @Override
    public Participation save(Participation participation) {
        return participationRepository.save(participation);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Participation> findAll(Pageable pageable) {
        return participationRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Participation updateScore(UUID participationId, Double score) {
        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> new RuntimeException("Participation not found"));
        participation.setScore(score);
        return participationRepository.save(participation);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CompetitionResultDTO> getUserCompetitionResults(UUID userId) {
        List<Participation> participations = participationRepository.findByAppUserId(userId);

        return participations.stream()
                .map(participation -> new CompetitionResultDTO(
                        participation.getCompetition().getId(),
                        participation.getCompetition().getCode(),
                        participation.getCompetition().getLocation(),
                        participation.getCompetition().getDate(),
                        participation.getScore()
                ))
                .collect(Collectors.toList());
    }
}
