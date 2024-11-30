package com.example.x_leagues.web.api.v1.species;



import com.example.x_leagues.model.Species;
import com.example.x_leagues.services.SpeciesService;
import com.example.x_leagues.web.vm.mapper.SpeciesMapper;
import com.example.x_leagues.web.vm.species.SpeciesResponseVM;
import com.example.x_leagues.web.vm.species.SpeciesVM;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/species")
@RestController
public class SpeciesController {


    private final SpeciesService speciesService;
    private final SpeciesMapper speciesMapper;

    public SpeciesController(SpeciesService speciesService , SpeciesMapper speciesMapper) {
        this.speciesService = speciesService;
        this.speciesMapper = speciesMapper;
    }


    @PostMapping("/save")
    public ResponseEntity<SpeciesResponseVM> save(@Valid @RequestBody SpeciesVM speciesVM){
        Species species = speciesMapper.toSpecies(speciesVM);
        Species savedSpecies = speciesService.save(species);
        SpeciesResponseVM speciesResponseVM = speciesMapper.toVM(savedSpecies);
        return ResponseEntity.ok(speciesResponseVM);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SpeciesResponseVM> findById(@PathVariable UUID id){
        Species species = speciesService.findById(id);
        SpeciesResponseVM speciesResponseVM = speciesMapper.toVM(species);
        return ResponseEntity.ok(speciesResponseVM);
    }


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<SpeciesResponseVM>> findAll(Pageable pageable){
        Page<Species> species = speciesService.findAll(pageable);
        Page<SpeciesResponseVM> speciesResponseVMS = species.map(speciesMapper::toVM);
        return ResponseEntity.ok(speciesResponseVMS);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SpeciesResponseVM> update(@PathVariable UUID id , @Valid @RequestBody SpeciesVM speciesVM){
        Species species = speciesMapper.toSpecies(speciesVM);
        Species updatedSpecies = speciesService.update(id , species);
        SpeciesResponseVM speciesResponseVM = speciesMapper.toVM(updatedSpecies);
        return ResponseEntity.ok(speciesResponseVM);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        speciesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
