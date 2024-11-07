package com.example.x_leagues.web.vm.mapper;


import com.example.x_leagues.model.Species;
import com.example.x_leagues.web.vm.species.SpeciesResponseVM;
import com.example.x_leagues.web.vm.species.SpeciesVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeciesMapper {

    Species toSpeciesResponse(SpeciesResponseVM speciesResponseVM);
    SpeciesResponseVM toVM(Species species);
    Species toSpecies(SpeciesVM speciesVM);
}
