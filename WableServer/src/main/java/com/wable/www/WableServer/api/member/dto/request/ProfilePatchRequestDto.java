package com.wable.www.WableServer.api.member.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record ProfilePatchRequestDto (
        String nickname,
        Boolean isAlarmAllowed,
        String memberIntro,
        Boolean isPushAlarmAllowed,
        String fcmToken,
		String memberFanTeam,
		Integer memberLckYears
){
}
