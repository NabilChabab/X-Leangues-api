package com.example.x_leagues.repository;

import com.example.x_leagues.model.Hunt;
import com.example.x_leagues.model.Participation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface HuntRepository extends JpaRepository<Hunt, UUID> , JpaSpecificationExecutor<Hunt> {
    List<Hunt> findAll(Specification<Hunt> specification);
    @Modifying
    @Transactional
    @Query(value = "CALL delete_hunts_by_species_id(:speciesId)", nativeQuery = true)
    void deleteBySpeciesIdUsingProcedure(@Param("speciesId") UUID speciesId);
    List<Hunt> findByParticipation(Participation participation);
}
