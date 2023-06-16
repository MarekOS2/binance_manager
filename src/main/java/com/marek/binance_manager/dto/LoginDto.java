package com.marek.binance_manager.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto (
        String username,
        String password
) {
}
