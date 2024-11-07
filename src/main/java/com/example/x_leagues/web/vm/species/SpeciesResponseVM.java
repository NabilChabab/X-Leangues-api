package com.example.x_leagues.web.vm.species;


import com.example.x_leagues.model.enums.Difficulty;
import com.example.x_leagues.model.enums.SpeciesType;
import lombok.Data;

import java.util.UUID;

@Data
public class SpeciesResponseVM {

    private UUID id;
    private String name;
    private SpeciesType category;
    private Double minimumWeight;
    private Difficulty difficulty;
    private Integer points;
}
