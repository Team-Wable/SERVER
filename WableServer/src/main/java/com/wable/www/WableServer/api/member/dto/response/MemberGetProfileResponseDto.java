package com.wable.www.WableServer.api.member.dto.response;

import com.wable.www.WableServer.api.member.domain.Member;

public record MemberGetProfileResponseDto(
        Long memberId,
        String nickname,
        String memberProfileUrl,
        String memberIntro,
        int memberGhost
) {
    public static MemberGetProfileResponseDto of(Member member, int memberGhost){
        return new MemberGetProfileResponseDto(
                member.getId(),
                member.getNickname(),
                member.getProfileUrl(),
                member.getMemberIntro(),
                memberGhost
        );
    }
}
