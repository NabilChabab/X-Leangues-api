package com.example.x_leagues.services.impl;


import com.example.x_leagues.model.Hunt;
import com.example.x_leagues.model.Participation;
import com.example.x_leagues.model.Species;
import com.example.x_leagues.repository.HuntRepository;
import com.example.x_leagues.repository.SpeciesRepository;
import com.example.x_leagues.services.SpeciesService;
import com.example.x_leagues.services.dto.HuntDTO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HuntServiceImpl {

    private final HuntRepository huntRepository;
    private final SpeciesServiceImpl speciesService;
    private final ParticipationServiceImpl participationService;

    public HuntServiceImpl(HuntRepository huntRepository, SpeciesServiceImpl speciesService,
                           @Lazy ParticipationServiceImpl participationService) {
        this.huntRepository = huntRepository;
        this.speciesService = speciesService;
        this.participationService = participationService;
    }

    public List<Hunt> findByParticipation(Participation participation) {
        return huntRepository.findByParticipation(participation);
    }

    public double registerHunt(HuntDTO huntRequest) {

        Participation participation = participationService.findById(huntRequest.getParticipationId());
        Species species = speciesService.findById(huntRequest.getSpeciesId());

        Hunt hunt = Hunt.builder()
                .species(species)
                .participation(participation)
                .weight(huntRequest.getWeight())
                .build();

        huntRepository.save(hunt);

        if (species.getMinimumWeight() <= hunt.getWeight()) {
            return participationService.updateScore(participation);
        }
        return participation.getScore();


    }
}
