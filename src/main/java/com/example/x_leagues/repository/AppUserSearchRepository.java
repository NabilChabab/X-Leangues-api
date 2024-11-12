package com.example.x_leagues.repository;

import com.example.x_leagues.services.dto.SearchDTO;

import java.util.List;

public interface AppUserSearchRepository {

    List<SearchDTO> findByCriteria(SearchDTO searchDTO);
}
