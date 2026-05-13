package com.homeloan.application.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWT bearer token for subsequent API calls")
public record AuthResponse(
        String tokenType,
        String accessToken,
        long expiresInSeconds,
        String username
) {
}
