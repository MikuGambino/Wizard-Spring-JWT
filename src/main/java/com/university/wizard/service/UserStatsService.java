package com.university.wizard.service;

import com.university.wizard.model.UserStats;
import com.university.wizard.model.dto.UserStatsDTO;
import com.university.wizard.repository.UserRepository;
import com.university.wizard.repository.UserStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserStatsService {
    private final UserStatsRepository userStatsRepository;

    public void saveUserStats(UserStats userStats) {
        userStatsRepository.save(userStats);
    }

    public void saveUserStats(UserStatsDTO userStatsDTO, Principal principal) {
        UserStats userStats = userStatsRepository.findUserStatsByUsername(principal.getName());

        userStats.setExp(userStatsDTO.getExp());
        userStats.setCharacterClass(userStatsDTO.getCharacterClass());
        userStats.setCharacterLevel(userStatsDTO.getCharacterLevel());
        userStats.setGameLevel(userStatsDTO.getGameLevel());

        userStatsRepository.save(userStats);
    }

    public void updateExp(BigDecimal exp, Principal principal) {
        UserStats userStats = userStatsRepository.findUserStatsByUsername(principal.getName());

        userStats.setExp(exp);

        userStatsRepository.save(userStats);
    }

    public void updateGameLevel(int level, Principal principal) {
        UserStats userStats = userStatsRepository.findUserStatsByUsername(principal.getName());

        userStats.setGameLevel(level);

        userStatsRepository.save(userStats);
    }

    public void updateCharacterLevel(int level, Principal principal) {
        UserStats userStats = userStatsRepository.findUserStatsByUsername(principal.getName());

        userStats.setCharacterLevel(level);

        userStatsRepository.save(userStats);
    }

    public void updateCharacterClass(String clazz, Principal principal) {
        UserStats userStats = userStatsRepository.findUserStatsByUsername(principal.getName());

        userStats.setCharacterClass(clazz);

        userStatsRepository.save(userStats);
    }

    public UserStatsDTO getUserStats(Principal principal) {
        UserStats userStats = userStatsRepository.findUserStatsByUsername(principal.getName());

        return UserStatsDTO.builder()
                .characterLevel(userStats.getCharacterLevel())
                .characterClass(userStats.getCharacterClass())
                .gameLevel(userStats.getGameLevel())
                .exp(userStats.getExp())
                .build();
    }
}

