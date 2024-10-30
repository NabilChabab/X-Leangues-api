package com.example.x_leagues.web.vm.participation;

import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.model.Competition;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;


@Data
public class ParticipationVM {

    @NotNull(message = "App User ID is required")
    private UUID appUserId;
    @NotNull(message = "Competition ID is required")
    private UUID competitionId;
    private double score;
}
