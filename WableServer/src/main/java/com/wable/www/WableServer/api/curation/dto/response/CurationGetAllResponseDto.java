package com.wable.www.WableServer.api.curation.dto.response;

import com.wable.www.WableServer.api.curation.domain.Curation;
import com.wable.www.WableServer.api.member.domain.Member;

public record CurationGetAllResponseDto(
		Long memberId,
		String memberProfileUrl,
		String memberNickname,
		Long curationId,
		String curationLink,
		String time
) {
	public static CurationGetAllResponseDto of(Member member, Curation curation, String time) {
		return new CurationGetAllResponseDto(
				member.getId(),
				member.getProfileUrl(),
				member.getNickname(),
				curation.getId(),
				curation.getCurationLink(),
				time
		);
	}
}
