package com.alibou.ecommerce.auth;

public record AuthResponse(
    String userId,
    String email,
    String firstname,
    String lastname,
    String token
) {
}

