package com.university.wizard.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Статистика пользователя")
public class UserStatsDTO {
    @NotNull
    @Schema(description = "Уровень персонажа")
    private Integer characterLevel;
    @NotNull
    @Schema(description = "Уровень игры")
    private Integer gameLevel;
    @NotNull
    @Schema(description = "Опыт персонажа")
    private BigDecimal exp;
    @NotBlank
    @Schema(description = "Класс персонажа")
    private String characterClass;
}
