package com.example.x_leagues.services.impl;


import com.example.x_leagues.exceptions.CompetitionException;
import com.example.x_leagues.model.Competition;
import com.example.x_leagues.repository.CompetitionRepository;
import com.example.x_leagues.services.CompetitionService;
import com.example.x_leagues.services.dto.CompetitionDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompetitionServiceImpl implements CompetitionService {



    private final CompetitionRepository competitionRepository;


    public CompetitionServiceImpl(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }


    @Transactional
    @Override
    public Competition save(Competition competition) {
        LocalDateTime competitionDate = competition.getDate();

        if (competition.getMinParticipants() == null || competition.getMaxParticipants() == null) {
            throw new CompetitionException("Min and Max participants must be defined.");
        }

        if (competition.getMinParticipants() > competition.getMaxParticipants()) {
            throw new CompetitionException("Min participants cannot be greater than Max participants.");
        }

        List<Competition> competitionsInSameWeek = competitionRepository.findCompetitionsInSameWeek(competitionDate);

        if (!competitionsInSameWeek.isEmpty()) {
            throw new CompetitionException("A competition already exists in the same week");
        }

        return competitionRepository.save(competition);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void closeRegistrations() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus24Hours = now.plusHours(24);
        List<Competition> competitions = competitionRepository.findCompetitionsStartingIn24Hours(now , nowPlus24Hours);
        for (Competition competition : competitions) {
            if (competition.getDate().minusHours(24).isBefore(now)) {
                competition.setOpenRegistration(false);
                competitionRepository.save(competition);
            }
        }
    }


    @Transactional(readOnly = true)
    @Override
    public Page<Competition> findAll(Pageable pageable) {
        return competitionRepository.findAll(pageable);
    }

    @Override
    public CompetitionDetailsDTO competitionDetails(UUID competitionId) {
        Competition competition = competitionRepository.findById(competitionId).orElseThrow(()-> new CompetitionException("Competition not found"));

        Integer numberOfParticipants = competition.getParticipations() != null ? competition.getParticipations().size() : 0;

        return new CompetitionDetailsDTO(
                competition.getLocation(),
                competition.getDate(),
                numberOfParticipants
        );
    }


}
