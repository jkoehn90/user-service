package com.jkoehn90.userservice.service;

import com.jkoehn90.userservice.dto.AuthResponse;
import com.jkoehn90.userservice.dto.LoginRequest;
import com.jkoehn90.userservice.dto.RegisterRequest;
import com.jkoehn90.userservice.entity.Role;
import com.jkoehn90.userservice.entity.User;
import com.jkoehn90.userservice.repository.UserRepository;
import com.jkoehn90.userservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Lombok: generates constructor for all final fields (constructor injector)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request){

        // Check if email is already taken
        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already registered.");
        }

        // Build and save the new user
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // BCrypt hash
                .role(Role.USER)
                .build();

        userRepository.save(user);

        // Generate and return JWT token
        String token = jwtUtil.generateToken(user.getEmail());
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse login(LoginRequest request){

        // Authenticate the user - throws exception if credentials are wrong
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                        )
        );

        // If authentication passed, fetch user and generate token
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found."));

        String token = jwtUtil.generateToken(user.getEmail());
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
