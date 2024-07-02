package com.university.wizard.util;
import com.university.wizard.exceptions.InvalidJwtException;
import com.university.wizard.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-token.expiration}")
    private Long accessTokenExpiration;
    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpiration, "isAccess");
    }

    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpiration, "isRefresh");
    }

    private String generateToken(User user, long expiration, String type) {
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + expiration);
        return Jwts.builder()
                .claim("type", type)
                .subject(user.getUsername())
                .issuedAt(issuedDate)
                .expiration(expiredDate)
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtException("JWT Token has expired", HttpStatus.UNAUTHORIZED);
        } catch (SignatureException e) {
            throw new InvalidJwtException("Invalid JWT signature", HttpStatus.UNAUTHORIZED);
        } catch (MalformedJwtException e) {
            throw new InvalidJwtException("Invalid JWT Token", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw new InvalidJwtException("Error processing JWT Access Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getAllClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }

    private String getTokenType(String token) {
        return getAllClaimsFromToken(token).get("type", String.class);
    }

    public Boolean isAccessTokenValid(String token, UserDetails userDetails) {
        String username = getUsername(token);
        String type = getTokenType(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token) && type.equals("isAccess");
    }

    public Boolean isRefreshTokenValid(String token, User user) {
        String username = getUsername(token);
        String type = getTokenType(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token) && type.equals("isRefresh");
    }
}
