package com.wable.www.WableServer.api.member.dto.response;

import com.wable.www.WableServer.api.member.domain.Member;

public record MemberGetProfileResponseDto(
        Long memberId,
        String nickname,
        String memberProfileUrl,
        String memberIntro,
        int memberGhost,
        String memberFanTeam,
        int memberLckYears,
        int memberLevel
) {
    public static MemberGetProfileResponseDto of(Member member, int memberGhost, int memberLevel){
        return new MemberGetProfileResponseDto(
                member.getId(),
                member.getNickname(),
                member.getProfileUrl(),
                member.getMemberIntro(),
                memberGhost,
                member.getMemberFanTeam(),
                member.getMemberLckYears(),
                memberLevel
        );
    }
}
