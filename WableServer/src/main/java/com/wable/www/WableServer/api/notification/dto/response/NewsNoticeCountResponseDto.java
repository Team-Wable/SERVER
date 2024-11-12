package com.wable.www.WableServer.api.notification.dto.response;

public record NewsNoticeCountResponseDto(
		int newsNumber,
		int noticeNumber
) {
	public static NewsNoticeCountResponseDto of(int newsNumber, int noticeNumber) {
		return new NewsNoticeCountResponseDto(
				newsNumber,
				noticeNumber
		);
	}
}
