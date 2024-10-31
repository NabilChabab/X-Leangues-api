package com.example.x_leagues.web.vm.competitions;

import com.example.x_leagues.annotations.CodeFormat;
import com.example.x_leagues.model.enums.SpeciesType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompetitionVM {


    @NotBlank(message = "Code is required")
    @CodeFormat
    private String code;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Date is required")
    @FutureOrPresent(message = "Date must be in the present or future")
    private LocalDateTime date;

    @NotNull(message = "Species type is required")
    @Enumerated(EnumType.STRING)
    private SpeciesType speciesType;

    @NotNull(message = "Minimum participants is required")
    @Min(value = 1, message = "Minimum participants should be at least 1")
    private Integer minParticipants;

    @NotNull(message = "Maximum participants is required")
    @Min(value = 1, message = "Maximum participants should be at least 1")
    private Integer maxParticipants;

    @NotNull(message = "Open registration status is required")
    private Boolean openRegistration;
}
