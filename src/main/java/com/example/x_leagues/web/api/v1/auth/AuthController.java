package com.example.x_leagues.web.api.v1.auth;
import com.example.x_leagues.services.dto.AuthenticationRequestDTO;
import com.example.x_leagues.services.dto.AuthenticationResponseDTO;
import com.example.x_leagues.services.dto.RegisterRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {


}
