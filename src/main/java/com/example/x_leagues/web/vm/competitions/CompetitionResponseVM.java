package com.example.x_leagues.web.vm.competitions;


import com.example.x_leagues.model.enums.SpeciesType;
import lombok.Data;

@Data
public class CompetitionResponseVM {
    private String code;
    private SpeciesType speciesType;
    private boolean openRegistration;
}
