package com.university.wizard.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Schema(description = "Имя пользователя", example = "Mike")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    @Schema(description = "Пароль")
    private String password;

    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Schema(description = "Адрес электронной почты", example = "jondoe@gmail.com")
    private String email;
}
