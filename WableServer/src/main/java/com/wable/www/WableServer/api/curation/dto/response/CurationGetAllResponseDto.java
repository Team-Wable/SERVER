package com.wable.www.WableServer.api.curation.dto.response;

import com.wable.www.WableServer.api.curation.domain.Curation;
import com.wable.www.WableServer.api.member.domain.Member;

public record CurationGetAllResponseDto(
		Long curationId,
		String curationLink,
		String curationTitle,
		String curationThumbnail,
		String time
) {
	public static CurationGetAllResponseDto of(Curation curation, String time) {
		return new CurationGetAllResponseDto(
				curation.getId(),
				curation.getCurationLink(),
				curation.getCurationTitle(),
				curation.getCurationThumbnail(),
				time
		);
	}
}
