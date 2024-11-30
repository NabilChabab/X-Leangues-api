package com.example.x_leagues.web.api.v1.user;

import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.services.dto.SearchDTO;
import com.example.x_leagues.services.impl.AppUserServiceImpl;
import com.example.x_leagues.web.vm.auth.ResponseVM;
import com.example.x_leagues.web.vm.mapper.AppUserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1")
public class AppUserController {


    private final AppUserServiceImpl appUserService;
    private final AppUserMapper appUserMapper;

    public AppUserController(AppUserServiceImpl appUserService, AppUserMapper appUserMapper) {
        this.appUserService = appUserService;
        this.appUserMapper = appUserMapper;
    }


    @GetMapping("/search-members")
    @PreAuthorize("hasAuthority('JURY')")
    public ResponseEntity<List<SearchDTO>> searchMembers(
            @RequestParam(required = false) String cin,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {
        SearchDTO searchDTO = new SearchDTO(cin, firstName, lastName);
        List<SearchDTO> users = appUserService.searchMembers(searchDTO);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ResponseVM> findById(@PathVariable UUID id){
        Optional<AppUser> appUser = appUserService.findById(id);
        ResponseVM responseVM = appUserMapper.toVM(appUser.orElse(null));
        return new ResponseEntity<>(responseVM, HttpStatus.OK);
    }


    @GetMapping("/users")
    public ResponseEntity<Page<ResponseVM>> findAll(Pageable pageable){
        Page<AppUser> appUsers = appUserService.findAll(pageable);
        Page<ResponseVM> responseVMS = appUsers.map(appUserMapper::toVM);
        return new ResponseEntity<>(responseVMS, HttpStatus.OK);
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<ResponseVM> update(@PathVariable UUID id, @RequestBody AppUser appUser){
        AppUser updatedAppUser = appUserService.update(id , appUser);
        ResponseVM responseVM = appUserMapper.toVM(updatedAppUser);
        return new ResponseEntity<>(responseVM, HttpStatus.OK);
    }




}
