package com.university.wizard.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Wizard",
                version = "1.0.0",
                contact = @Contact(
                        name = "Kuznetsov Daniil",
                        email = "dan.k-2011@yandex.ru",
                        url = "https://vk.com/mikegambino"
                ),
                license = @License(
                        name = "MIT License"
                )
        )
)
public class SwaggerConfig {
}

