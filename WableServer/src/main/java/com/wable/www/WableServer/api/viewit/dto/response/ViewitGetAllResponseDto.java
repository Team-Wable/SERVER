package com.wable.www.WableServer.api.viewit.dto.response;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.viewit.domain.Viewit;

public record ViewitGetAllResponseDto(
		Long memberId,
		String memberProfileUrl,
		String memberNickname,
		Long viewitId,
		String viewitImage,
		String viewitLink,
		String viewitTitle,
		String viewitText,
		String viewitName,
		String time,
		boolean isLiked,
		int likedNumber,
		Boolean isBlind
) {
	public static ViewitGetAllResponseDto of(Member member, Viewit viewit, String time, boolean isLiked, int likedNumber) {
		return new ViewitGetAllResponseDto(
				member.getId(),
				member.getProfileUrl(),
				member.getNickname(),
				viewit.getId(),
				viewit.getViewitImage(),
				viewit.getViewitLink(),
				viewit.getViewitTitle(),
				viewit.getViewitText(),
				viewit.getViewitName(),
				time,
				isLiked,
				likedNumber,
				viewit.isBlind()
		);
	}
}
