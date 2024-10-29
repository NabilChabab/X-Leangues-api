package com.example.x_leagues.services.impl;


import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.services.AppUserService;
import com.example.x_leagues.exceptions.UserNotFoundException;
import com.example.x_leagues.repository.AppUserRepository;
import com.example.x_leagues.utils.PasswordEncoderUtil;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class AppUserServiceImpl implements AppUserService {


    @Autowired
    private AppUserRepository appUserRepository;


    @Autowired
    private PasswordEncoderUtil passwordEncoder;

    @Transactional
    @Override
    public AppUser save(@Valid AppUser appUser) {
        String encodedPassword = passwordEncoder.encodePassword(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        Optional<AppUser> appUserOptional = appUserRepository.findByUsernameOrEmail(appUser.getUsername(), appUser.getEmail());
        if (appUserOptional.isPresent()){
            throw new RuntimeException("User already exists");
        }
        return appUserRepository.save(appUser);
    }

    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    @Override
    public AppUser findById(UUID id){
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new UserNotFoundException("AppUser not found with id : " + id));
        return appUser;
    }
}
