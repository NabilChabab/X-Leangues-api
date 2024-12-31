package com.example.x_leagues.services.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserParticipationCountDTO{

    private UUID userId;
    private String username;
    private String nationality;
    private LocalDateTime joinDate;
    private Long participationCount;
}
