package com.wable.www.WableServer.api.community.dto.response;

public record GetCommunityListResponseDto(
		String communityName,
		double communityNum
) {
	public static GetCommunityListResponseDto of(String communityName, double communityNum) {
		return new GetCommunityListResponseDto(
				communityName,
				communityNum
		);
	}
}
