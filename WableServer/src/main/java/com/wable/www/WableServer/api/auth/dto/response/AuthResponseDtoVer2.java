package com.wable.www.WableServer.api.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class AuthResponseDtoVer2 {

    private String nickName;

    private Long memberId;

    private String accessToken;

    private String refreshToken;

    private String memberProfileUrl;

    private Boolean isNewUser;

    private Boolean isPushAlarmAllowed;

    private String memberFanTeam;

    private int memberLckYears;

    private int memberLevel;

    private Boolean isAdmin;
}
