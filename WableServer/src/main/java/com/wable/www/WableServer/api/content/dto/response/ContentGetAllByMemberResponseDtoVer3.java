package com.wable.www.WableServer.api.content.dto.response;

import com.wable.www.WableServer.api.content.domain.Content;
import com.wable.www.WableServer.api.member.domain.Member;

public record ContentGetAllByMemberResponseDtoVer3(
        Long memberId,
        String memberProfileUrl,
        String memberNickname,
        Long contentId,
        String contentTitle,
        String contentText,
        String time,
        boolean isGhost,
        int memberGhost,
        boolean isLiked,
        int likedNumber,
        int commentNumber,
        String contentImageUrl,
        String memberFanTeam,
        Boolean isBlind

) {
    public static ContentGetAllByMemberResponseDtoVer3 of(Member writerMember, int writerGhost, Content content, boolean isGhost, boolean isLiked, String time, int likedNumber, int commentNumber) {
        return new ContentGetAllByMemberResponseDtoVer3(
                writerMember.getId(),
                writerMember.getProfileUrl(),
                writerMember.getNickname(),
                content.getId(),
                content.getContentTitle(),
                content.getContentText(),
                time,
                isGhost,
                writerGhost,
                isLiked,
                likedNumber,
                commentNumber,
                content.getContentImage(),
                writerMember.getMemberFanTeam(),
                content.isBlind()
        );
    }
}