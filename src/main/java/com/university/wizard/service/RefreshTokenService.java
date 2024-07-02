package com.university.wizard.service;

import com.university.wizard.exceptions.TokenRefreshException;
import com.university.wizard.model.RefreshToken;
import com.university.wizard.model.User;
import com.university.wizard.repository.RefreshTokenRepository;
import com.university.wizard.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Transactional
    public String createRefreshToken(User user) {
        deleteByUsername(user);

        String tokenString = jwtUtils.generateRefreshToken(user);

        RefreshToken refreshToken = new RefreshToken(
                user,
                tokenString,
                Instant.now().plusMillis(refreshTokenDurationMs)
        );

        refreshTokenRepository.save(refreshToken);
        return tokenString;
    }

    public void verifyExpiration(String token, String username) {
        User user = userService.getByUsername(username);

        if (!jwtUtils.isRefreshTokenValid(token, user)) {
            throw new TokenRefreshException(token, "Refresh token is invalid");
        }

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenRefreshException(token, "Refresh token is not in database!"));

        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException(token, "Refresh token was expired. Please make a new signin request");
        }
    }

    public void deleteByUsername(User user) {
        refreshTokenRepository.deleteByUser(user.getId().intValue());
    }
}