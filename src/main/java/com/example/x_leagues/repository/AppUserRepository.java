package com.example.x_leagues.repository;

import com.example.x_leagues.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface AppUserRepository extends JpaRepository<AppUser, UUID> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<AppUser> findByUsernameOrEmail(String username, String email);
    Optional<AppUser> findByUsername(String username);
    @Query("SELECT u FROM AppUser u " +
            "WHERE (:cin IS NULL OR u.cin = :cin) " +
            "AND (:firstName IS NULL OR CAST(u.firstName AS string) LIKE CONCAT('%', :firstName, '%')) " +
            "AND (:lastName IS NULL OR CAST(u.lastName AS string) LIKE CONCAT('%', :lastName, '%'))")
    List<AppUser> findByCriteria(@Param("cin") String cin,
                                 @Param("firstName") String firstName,
                                 @Param("lastName") String lastName);
}
