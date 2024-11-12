package com.example.x_leagues.services.dto;

import com.example.x_leagues.model.enums.Difficulty;
import com.example.x_leagues.model.enums.SpeciesType;
import lombok.Data;

import java.util.UUID;


@Data
public class HuntDTO {

    private UUID participationId;

    private UUID speciesId;

    private Double weight;
}
