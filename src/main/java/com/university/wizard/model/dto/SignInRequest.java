package com.university.wizard.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {
    @NotBlank
    @Schema(description = "Имя пользователя", example = "Mike")
    private String username;
    @NotBlank
    @Schema(description = "Пароль")
    private String password;
}
