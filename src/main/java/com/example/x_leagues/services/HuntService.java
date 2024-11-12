package com.example.x_leagues.services;

import com.example.x_leagues.model.Hunt;

import java.util.List;
import java.util.UUID;

public interface HuntService {
    List<Hunt> getValidHuntsForParticipation(UUID participationId);
}
