package com.example.x_leagues.services.impl;


import com.example.x_leagues.exceptions.CompetitionAlreadyExistException;
import com.example.x_leagues.model.Competition;
import com.example.x_leagues.repository.CompetitionRepository;
import com.example.x_leagues.services.CompetitionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompetitionServiceImpl implements CompetitionService {



    private final CompetitionRepository competitionRepository;


    public CompetitionServiceImpl(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }



    @Override
    public Competition save(Competition competition){
        Optional<Competition> competitionOptional = competitionRepository.findByCode(competition.getCode());
        if (competitionOptional.isPresent()){
            throw new CompetitionAlreadyExistException("Competition already exists");
        }
        return competitionRepository.save(competition);
    }
}
