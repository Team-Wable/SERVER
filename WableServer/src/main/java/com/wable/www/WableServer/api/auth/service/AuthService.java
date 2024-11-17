package com.wable.www.WableServer.api.auth.service;

import com.wable.www.WableServer.api.auth.dto.request.AuthRequestDto;
import com.wable.www.WableServer.api.auth.dto.response.AuthResponseDto;
import com.wable.www.WableServer.api.auth.dto.response.AuthTokenResponseDto;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface AuthService {
    //소셜 로그인
    AuthResponseDto socialLogin(String socialAccessToken, AuthRequestDto authRequestDto) throws NoSuchAlgorithmException, InvalidKeySpecException;

    //새로운 토큰 발급
    AuthTokenResponseDto getNewToken(String accessToken, String refreshToken);
}


