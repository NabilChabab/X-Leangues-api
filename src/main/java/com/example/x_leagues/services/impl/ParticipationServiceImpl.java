package com.example.x_leagues.services.impl;

import com.example.x_leagues.exceptions.ParticipationException;
import com.example.x_leagues.model.Participation;
import com.example.x_leagues.repository.ParticipationRepository;
import com.example.x_leagues.services.ParticipationService;
import com.example.x_leagues.services.dto.CompetitionHistoryDTO;
import com.example.x_leagues.services.dto.CompetitionResultDTO;
import com.example.x_leagues.services.dto.PodiumDTO;
import com.example.x_leagues.services.dto.ScoreUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        if (participation.getAppUser() == null || participation.getCompetition() == null) {
            throw new ParticipationException("User ID or Competition ID cannot be null.");
        }

        LocalDateTime competitionStartDate = participation.getCompetition().getDate();
        LocalDateTime now = LocalDateTime.now();

        if (!participation.getCompetition().getOpenRegistration() || competitionStartDate.minusHours(24).isBefore(now)) {
            throw new ParticipationException("Registration is closed for this competition.");
        }

        LocalDate today = LocalDate.now();
        LocalDate licenseExpirationDate = participation.getAppUser().getLicenseExpirationDate().toLocalDate();

        if (licenseExpirationDate.isBefore(today)) {
            throw new ParticipationException("Participation not allowed: the user's license is expired.");
        }

        int maxParticipants = participation.getCompetition().getMaxParticipants();
        long currentParticipantsCount = participationRepository.countByCompetitionId(participation.getCompetition().getId());

        if (currentParticipantsCount >= maxParticipants) {
            throw new ParticipationException("Cannot join: The maximum number of participants for this competition has been reached.");
        }

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

    @Override
    public List<PodiumDTO> getCompetitionPodium(UUID competitionId) {
        List<Participation> topParticipants = participationRepository.findTop3ByCompetitionIdOrderByScoreDesc(competitionId);

        return topParticipants.stream()
                .map(participation -> new PodiumDTO(
                        participation.getId(),
                        participation.getAppUser().getUsername(),
                        participation.getScore()
                ))
                .collect(Collectors.toList());

    }

    @Override
    public Page<CompetitionHistoryDTO> getUserCompetitionHistory(UUID userId, Pageable pageable) {
        Page<Participation> pastParticipations = participationRepository.findPastCompetitionsByAppUserId(userId, pageable);

        return pastParticipations.map(participation -> {
            return new CompetitionHistoryDTO(
                    participation.getCompetition().getId(),
                    participation.getCompetition().getDate(),
                    participation.getCompetition().getLocation(),
                    participation.getScore(),
                    calculateRank(participation)
            );
        });
    }

    private Integer calculateRank(Participation participation) {
        List<Participation> sortedParticipants = participation.getCompetition().getParticipations()
                .stream()
                .sorted((p1, p2) -> Double.compare(p2.getScore(), p1.getScore()))
                .collect(Collectors.toList());

        for (int i = 0; i < sortedParticipants.size(); i++) {
            if (sortedParticipants.get(i).getId().equals(participation.getId())) {
                return i + 1;
            }
        }
        return null;
    }
}
