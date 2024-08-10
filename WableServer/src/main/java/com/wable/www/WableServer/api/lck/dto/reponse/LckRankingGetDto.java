package com.wable.www.WableServer.api.lck.dto.reponse;

import com.wable.www.WableServer.api.lck.domain.LckRanking;

public record LckRankingGetDto(
		int teamRank,
		String teamName,
		int teamWin,
		int teamDefeat,
		double winningRate,
		int scoreDiff
) {
	public static LckRankingGetDto of(LckRanking lckRanking, double winningRate) {
		return new LckRankingGetDto(
				lckRanking.getLckRanking(),
				lckRanking.getLckTeamName(),
				lckRanking.getTeamWin(),
				lckRanking.getTeamDefeat(),
				winningRate,
				lckRanking.getTeamScoreDiff()
		);
	}
}
