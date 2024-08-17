package com.wable.www.WableServer.api.lck.dto.reponse;

import java.util.List;

public record LckScheduleByDateDto(
		String date,
		List<LckScheduleGetDto> games
) {
	public static LckScheduleByDateDto of(String date, List<LckScheduleGetDto> games) {
		return new LckScheduleByDateDto(
				date,
				games
		);
	}
}
