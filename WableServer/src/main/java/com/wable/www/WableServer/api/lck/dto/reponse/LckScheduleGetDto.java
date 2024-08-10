package com.wable.www.WableServer.api.lck.dto.reponse;

import com.wable.www.WableServer.api.lck.domain.LckSchedule;

public record LckScheduleGetDto(
		String gameDate,
		String aTeamName,
		int aTeamScore,
		String bTeamName,
		int bTeamScore,
		String gameStatus
) {
	public static LckScheduleGetDto of(LckSchedule lckSchedule, String gameDate) {
		return new LckScheduleGetDto(
				gameDate,
				lckSchedule.getTeamAName(),
				lckSchedule.getTeamAScore(),
				lckSchedule.getTeamBName(),
				lckSchedule.getTeamBScore(),
				lckSchedule.getGameState().name()
		);
	}
}
