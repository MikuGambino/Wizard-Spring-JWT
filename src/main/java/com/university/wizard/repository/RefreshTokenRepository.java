package com.university.wizard.repository;

import com.university.wizard.model.RefreshToken;
import com.university.wizard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    @Query("""
            delete
            from RefreshToken t
            where t.user.id = :id
            """)
    @Modifying
    void deleteByUser(int id);
}