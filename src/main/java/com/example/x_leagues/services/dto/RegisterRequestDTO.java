package com.example.x_leagues.services.dto;


import com.example.x_leagues.annotations.UniqueEmail;
import com.example.x_leagues.annotations.UniqueUsername;
import com.example.x_leagues.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {


    @NotBlank(message = "Username is required")
    @UniqueUsername(message = "Username is already taken")
    private String username;

    @NotBlank(message = "Email is required")
    @Email
    @UniqueEmail(message = "Email is already taken")
    private String email;

    @NotBlank(message = "password cannot be blank")
    @Size(min = 8, message = "password must be at least 8 characters")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
        message = "password must contain at least one uppercase letter, one lowercase letter, and one number"
    )
    private String password;

    private Role role;
}
