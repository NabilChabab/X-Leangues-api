package com.example.x_leagues.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CompetitionDetailsDTO {

    private String location;
    private LocalDateTime date;
    private Integer numberOfParticipants;
}
