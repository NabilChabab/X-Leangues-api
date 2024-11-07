package com.example.x_leagues.repository;

import com.example.x_leagues.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParticipationRepository extends JpaRepository<Participation , UUID> {
    List<Participation> findByAppUserId(UUID userId);
}
