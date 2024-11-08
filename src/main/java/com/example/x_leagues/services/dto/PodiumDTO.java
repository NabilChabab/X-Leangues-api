package com.example.x_leagues.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PodiumDTO {
    private UUID participantId;
    private String username;
    private Double score;
}
