package com.example.x_leagues.repository;

import com.example.x_leagues.model.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpeciesRepository extends JpaRepository<Species , UUID> {
}
