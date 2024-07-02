package com.university.wizard.service;

import com.university.wizard.exceptions.ResourceAlreadyExistsException;
import com.university.wizard.model.User;
import com.university.wizard.model.UserStats;
import com.university.wizard.model.dto.JwtAuthenticationResponse;
import com.university.wizard.model.dto.SignInRequest;
import com.university.wizard.model.dto.SignUpRequest;
import com.university.wizard.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final UserStatsService userStatsService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        if (userService.getByUsername(request.getUsername()).isPresent()) {
            throw new ResourceAlreadyExistsException("Username", request.getUsername());
        }

        if (userService.getByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email", request.getEmail());
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        userService.saveUser(user);

        UserStats userStats = UserStats.builder()
                .user(user)
                .exp(BigDecimal.ZERO)
                .characterClass("wizard")
                .gameLevel(1)
                .characterLevel(1)
                .build();

        userStatsService.saveUserStats(userStats);

        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        return new JwtAuthenticationResponse(jwtUtils.generateToken(userDetails));
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        UserDetails user = userService.loadUserByUsername(request.getUsername());

        String jwt = jwtUtils.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
