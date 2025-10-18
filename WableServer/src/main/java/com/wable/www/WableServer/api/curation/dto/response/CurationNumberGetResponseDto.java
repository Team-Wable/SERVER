package com.wable.www.WableServer.api.curation.dto.response;

public record CurationNumberGetResponseDto(
		Long curationId
) {
	public static CurationNumberGetResponseDto of(Long curationId) {
		return new CurationNumberGetResponseDto(
				curationId
		);
	}
}
