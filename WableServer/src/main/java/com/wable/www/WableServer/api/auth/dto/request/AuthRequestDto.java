package com.wable.www.WableServer.api.auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class AuthRequestDto {
    @NotNull
    private String socialPlatform;

    private String userName;
}