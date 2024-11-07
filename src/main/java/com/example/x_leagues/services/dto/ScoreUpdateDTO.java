package com.example.x_leagues.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ScoreUpdateDTO {

    private UUID participationId;
    private Double score;

}
