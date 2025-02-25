package com.wable.www.WableServer.api.community.dto.response;

public record GetMemberCommunityResponseDto(
		String community
) {
	public static GetMemberCommunityResponseDto of(String memberCommunity) {
		return new GetMemberCommunityResponseDto(
				memberCommunity
		);
	}
}
