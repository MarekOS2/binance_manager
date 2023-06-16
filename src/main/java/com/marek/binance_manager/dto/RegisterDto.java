package com.marek.binance_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.jetbrains.annotations.NotNull;

public record RegisterDto(
        @NotBlank(message = "name should not be blank")
        String username,
        @Email
        String email,
        @NotBlank @Pattern(regexp = ".{8,}", message = "password not valid")
        String password
){
}
