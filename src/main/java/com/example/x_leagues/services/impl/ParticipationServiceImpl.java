package com.example.x_leagues.services.impl;

import com.example.x_leagues.model.Participation;
import com.example.x_leagues.repository.ParticipationRepository;
import com.example.x_leagues.services.ParticipationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


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
}
