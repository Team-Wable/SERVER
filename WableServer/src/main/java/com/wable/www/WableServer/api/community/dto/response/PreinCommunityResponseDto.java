package com.wable.www.WableServer.api.community.dto.response;

public record PreinCommunityResponseDto(
		double communityNum
) {
	public static PreinCommunityResponseDto of(double communityNum) {
		return new PreinCommunityResponseDto(
				communityNum
		);
	}
}
