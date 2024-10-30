package com.example.x_leagues.repository;

import com.example.x_leagues.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompetitionRepository extends JpaRepository<Competition , UUID> {


    Optional<Competition> findByCode(String code);
}
