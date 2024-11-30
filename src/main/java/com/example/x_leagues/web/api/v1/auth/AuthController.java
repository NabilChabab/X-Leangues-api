package com.example.x_leagues.web.api.v1.auth;
import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.model.enums.Role;
import com.example.x_leagues.services.dto.AuthenticationRequestDTO;
import com.example.x_leagues.services.dto.AuthenticationResponseDTO;
import com.example.x_leagues.services.dto.RegisterRequestDTO;
import com.example.x_leagues.services.impl.AuthenticationService;
import com.example.x_leagues.web.vm.auth.LoginVM;
import com.example.x_leagues.web.vm.auth.ResponseVM;
import com.example.x_leagues.web.vm.auth.RegisterVM;
import com.example.x_leagues.web.vm.mapper.AppUserMapper;
import com.example.x_leagues.services.impl.AppUserServiceImpl;
import com.example.x_leagues.web.vm.mapper.LoginMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;


import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(
        @RequestBody  RegisterRequestDTO request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
        @RequestBody @Valid AuthenticationRequestDTO request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

}
