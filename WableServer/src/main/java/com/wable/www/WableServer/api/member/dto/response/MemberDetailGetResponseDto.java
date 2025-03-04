package com.wable.www.WableServer.api.member.dto.response;

import com.wable.www.WableServer.api.member.domain.Member;

public record MemberDetailGetResponseDto(
        Long memberId,
        String joinDate,
        String showMemberId,
        String socialPlatform,
        String versionInformation
) {
    public static MemberDetailGetResponseDto of(Member member, String joinDate){
        return new MemberDetailGetResponseDto(
                member.getId(),
                joinDate,
                member.getMemberEmail(),
                member.getSocialPlatform().name() + " SOCIAL LOGIN",
                "1.0.1"
        );
    }
}
