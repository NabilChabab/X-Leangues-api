package com.example.x_leagues.web.api;
import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.services.dto.AppUserDTO;
import com.example.x_leagues.web.vm.mapper.AppUserMapper;
import com.example.x_leagues.services.impl.AppUserServiceImpl;
import com.example.x_leagues.web.vm.AppUserVm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class AppUserController {

    @Autowired
    private AppUserServiceImpl appUserService;

    @Autowired
    AppUserMapper appUserMapper;


    @PostMapping
    public ResponseEntity<AppUserDTO> save(@RequestBody @Valid AppUserVm appUserVm){
        AppUser appUser = appUserMapper.toEntity(appUserVm);
        AppUser savedAppUser = appUserService.save(appUser);
        AppUserDTO appUserDTO = appUserMapper.toDTO(savedAppUser);
        return new ResponseEntity<>(appUserDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDTO> findById(@PathVariable UUID id){
        AppUser appUser = appUserService.findById(id);
        AppUserDTO appUserDTO = appUserMapper.toDTO(appUser);
        return new ResponseEntity<>(appUserDTO, HttpStatus.OK);
    }

}
