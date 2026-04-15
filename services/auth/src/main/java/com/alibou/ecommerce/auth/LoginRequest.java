package com.alibou.ecommerce.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    String email,

    @NotNull(message = "Password is required")
    String password
) {
}

