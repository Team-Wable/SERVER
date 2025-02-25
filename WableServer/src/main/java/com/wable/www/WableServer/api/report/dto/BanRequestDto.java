package com.wable.www.WableServer.api.report.dto;

public record BanRequestDto(
		Long memberId,
		String triggerType,
		Long triggerId
) {
}
