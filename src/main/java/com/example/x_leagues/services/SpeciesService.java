package com.example.x_leagues.services;

import com.example.x_leagues.model.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SpeciesService {

    Page<Species> findAll(Pageable pageable);

    Species save(Species species);

    Species findById(UUID id);

    void delete(UUID id);

    Species update(UUID id, Species species);


}
