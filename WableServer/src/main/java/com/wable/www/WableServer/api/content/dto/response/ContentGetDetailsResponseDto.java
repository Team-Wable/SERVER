package com.wable.www.WableServer.api.content.dto.response;

import com.wable.www.WableServer.api.content.domain.Content;
import com.wable.www.WableServer.api.member.domain.Member;

public record ContentGetDetailsResponseDto(
        Long memberId,
        String memberProfileUrl,
        String memberNickname,
        boolean isGhost,
        int memberGhost,
        boolean isLiked,
        String time,
        int likedNumber,
        int commentNumber,
        String contentTitle,
        String contentText,
        String memberFanTeam

) {
    public static ContentGetDetailsResponseDto of(Member member, int memberGhost, Content content, boolean isGhost, boolean isLiked, String time, int likedNumber, int commentNumber){
        return new ContentGetDetailsResponseDto(
                member.getId(),
                member.getProfileUrl(),
                member.getNickname(),
                isGhost,
                memberGhost,
                isLiked,
                time,
                likedNumber,
                commentNumber,
                content.getContentTitle(),
                content.getContentText(),
                member.getMemberFanTeam()
        );
    }
}
