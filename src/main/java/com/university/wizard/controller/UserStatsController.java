package com.university.wizard.controller;

import com.university.wizard.model.dto.UserStatsDTO;
import com.university.wizard.service.UserStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stats")
@Tag(name = "Статистика")
public class UserStatsController {

    private final UserStatsService userStatsService;

    @PutMapping
    @Operation(summary = "Обновление всей статистики пользователя")
    public ResponseEntity<Void> updateStats(@RequestBody @Valid UserStatsDTO stats, Principal principal) {
        userStatsService.saveUserStats(stats, principal);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/exp")
    @Operation(summary = "Обновление опыта")
    public ResponseEntity<Void> updateExp(@RequestBody @Min(0) BigDecimal exp, Principal principal) {
        userStatsService.updateExp(exp, principal);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/game-lvl")
    @Operation(summary = "Обновление уровня игры")
    public ResponseEntity<Void> updateGameLevel(@RequestBody @Min(1) int gameLevel, Principal principal) {
        userStatsService.updateGameLevel(gameLevel, principal);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/character-lvl")
    @Operation(summary = "Обновление уровня персонажа")
    public ResponseEntity<Void> updateCharacterLevel(@RequestBody @Min(1) int characterLevel, Principal principal) {
        userStatsService.updateCharacterLevel(characterLevel, principal);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/character-class")
    @Operation(summary = "Обновление класса персонажа")
    public ResponseEntity<Void> updateCharacterClass(
            @RequestBody @NotBlank String characterClass,
            Principal principal) {
        userStatsService.updateCharacterClass(characterClass, principal);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Получить статистику пользователя")
    public ResponseEntity<UserStatsDTO> getUserStats(Principal principal) {
        UserStatsDTO userStatsDTO = userStatsService.getUserStats(principal);

        return ResponseEntity.ok(userStatsDTO);
    }
}
