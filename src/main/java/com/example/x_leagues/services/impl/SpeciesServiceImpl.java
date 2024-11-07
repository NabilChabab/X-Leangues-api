package com.example.x_leagues.services.impl;

import com.example.x_leagues.model.Hunt;
import com.example.x_leagues.model.Species;
import com.example.x_leagues.repository.HuntRepository;
import com.example.x_leagues.repository.SpeciesRepository;
import com.example.x_leagues.services.SpeciesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    private final SpeciesRepository speciesRepository;
    private final HuntRepository huntRepository;

    public SpeciesServiceImpl(SpeciesRepository speciesRepository, HuntRepository huntRepository) {
        this.speciesRepository = speciesRepository;
        this.huntRepository = huntRepository;
    }

    @Override
    public Page<Species> findAll(Pageable pageable) {
        return speciesRepository.findAll(pageable);
    }

    @Override
    public Species save(Species species) {
        return speciesRepository.save(species);
    }

    @Override
    public Species findById(UUID id) {
        return speciesRepository.findById(id).orElse(null);
    }


    @Transactional
    @Override
    public void delete(UUID speciesId) {
        Species species = speciesRepository.findById(speciesId)
                .orElseThrow(() -> new RuntimeException("Species not found"));

        huntRepository.deleteBySpeciesId(speciesId);

        speciesRepository.delete(species);
    }

        @Override
    public Species update(UUID id, Species species) {
        Species existingSpecies = speciesRepository.findById(id).orElseThrow(() -> new RuntimeException("Species not found"));
        existingSpecies.setName(species.getName());
        existingSpecies.setCategory(species.getCategory());
        existingSpecies.setMinimumWeight(species.getMinimumWeight());
        existingSpecies.setDifficulty(species.getDifficulty());
        existingSpecies.setPoints(species.getPoints());
        return speciesRepository.save(existingSpecies);
    }
}
