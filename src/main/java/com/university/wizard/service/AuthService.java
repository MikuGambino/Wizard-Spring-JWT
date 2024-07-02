package com.university.wizard.service;

import com.university.wizard.exceptions.ResourceAlreadyExistsException;
import com.university.wizard.exceptions.TokenRefreshException;
import com.university.wizard.model.RefreshToken;
import com.university.wizard.model.User;
import com.university.wizard.model.UserStats;
import com.university.wizard.model.dto.JwtAuthenticationResponse;
import com.university.wizard.model.dto.RefreshTokenRequest;
import com.university.wizard.model.dto.SignInRequest;
import com.university.wizard.model.dto.SignUpRequest;
import com.university.wizard.repository.RefreshTokenRepository;
import com.university.wizard.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final UserStatsService userStatsService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        if (userService.userExists(request.getUsername())) {
            throw new ResourceAlreadyExistsException("Username", request.getUsername());
        }

        if (userService.getByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email", request.getEmail());
        }

        Logger.getAnonymousLogger().info("here1");

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        User newUser = userService.saveUser(user);

        UserStats userStats = UserStats.builder()
                .user(user)
                .exp(BigDecimal.ZERO)
                .characterClass("wizard")
                .gameLevel(1)
                .characterLevel(1)
                .build();

        userStatsService.saveUserStats(userStats);

        Logger.getAnonymousLogger().info("here2");

        String refreshToken = refreshTokenService.createRefreshToken(newUser);
        String accessToken = jwtUtils.generateAccessToken(newUser);
        return JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        User user = userService.getByUsername(request.getUsername());

        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user);
        return JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String username = jwtUtils.getUsername(request.getRefreshToken());
        refreshTokenService.verifyExpiration(request.getRefreshToken(), username);

        User user = userService.getByUsername(username);

        String refreshToken = refreshTokenService.createRefreshToken(user);
        String accessToken = jwtUtils.generateAccessToken(user);
        return JwtAuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
