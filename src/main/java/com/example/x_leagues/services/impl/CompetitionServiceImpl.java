package com.example.x_leagues.services.impl;


import com.example.x_leagues.exceptions.CompetitionException;
import com.example.x_leagues.model.Competition;
import com.example.x_leagues.repository.CompetitionRepository;
import com.example.x_leagues.services.CompetitionService;
import com.example.x_leagues.services.dto.CompetitionDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.springframework.data.domain.Pageable;
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
    public Competition save(Competition competition){
        Optional<Competition> competitionOptional = competitionRepository.findByCode(competition.getCode());
        if (competitionOptional.isPresent()){
            throw new CompetitionException("Competition already exists");
        }
        return competitionRepository.save(competition);
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
