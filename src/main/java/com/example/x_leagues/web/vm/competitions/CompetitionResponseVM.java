package com.example.x_leagues.web.vm.competitions;


import com.example.x_leagues.model.enums.SpeciesType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompetitionResponseVM {
    private String location;
    private LocalDateTime date;
    private Integer numberOfParticipants;
}
