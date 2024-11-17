package com.wable.www.WableServer.api.auth.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenResponseDto {
    private String accessToken;

    private String refreshToken;
    public static AuthTokenResponseDto of (String accessToken, String refreshToken) {
        return new AuthTokenResponseDto(accessToken, refreshToken);
    }
}