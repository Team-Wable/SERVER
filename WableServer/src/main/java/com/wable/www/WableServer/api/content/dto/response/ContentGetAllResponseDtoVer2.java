package com.wable.www.WableServer.api.content.dto.response;

import com.wable.www.WableServer.api.content.domain.Content;
import com.wable.www.WableServer.api.member.domain.Member;

public record ContentGetAllResponseDtoVer2(
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
        Boolean isDeleted
) {
    public static ContentGetAllResponseDtoVer2 of(Member writerMember, Content content, boolean isGhost, int refinedMemberGhost, boolean isLiked, String time, int likedNumber, int commentNumber) {
        return new ContentGetAllResponseDtoVer2(
                writerMember.getId(),
                writerMember.getProfileUrl(),
                writerMember.getNickname(),
                content.getId(),
                content.getContentTitle(),
                content.getContentText(),
                time,
                isGhost,
                refinedMemberGhost,
                isLiked,
                likedNumber,
                commentNumber,
                writerMember.isDeleted()
        );
    }
}
