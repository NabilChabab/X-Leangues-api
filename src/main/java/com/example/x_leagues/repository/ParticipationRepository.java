package com.example.x_leagues.repository;

import com.example.x_leagues.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParticipationRepository extends JpaRepository<Participation , UUID> {
}
