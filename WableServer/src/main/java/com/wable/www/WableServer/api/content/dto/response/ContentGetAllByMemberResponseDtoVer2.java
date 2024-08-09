package com.wable.www.WableServer.api.content.dto.response;

import com.wable.www.WableServer.api.content.domain.Content;
import com.wable.www.WableServer.api.member.domain.Member;

public record ContentGetAllByMemberResponseDtoVer2(
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
        String contentImageUrl
) {
    public static ContentGetAllByMemberResponseDtoVer2 of(Member writerMember, int writerGhost, Content content, boolean isGhost, boolean isLiked, String time, int likedNumber, int commentNumber) {
        return new ContentGetAllByMemberResponseDtoVer2(
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
                content.getContentImage()
        );
    }
}