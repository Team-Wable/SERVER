package com.wable.www.WableServer.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SocialInfoDto {
    private String id;
    private String nickname;
//    private String profileUrl;
    private String email;
}