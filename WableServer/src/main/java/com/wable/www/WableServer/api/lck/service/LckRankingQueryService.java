package com.wable.www.WableServer.api.lck.service;

import com.wable.www.WableServer.api.lck.domain.LckRanking;
import com.wable.www.WableServer.api.lck.dto.reponse.LckRankingGetDto;
import com.wable.www.WableServer.api.lck.repository.LckRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LckRankingQueryService {
	private final LckRankingRepository lckRankingRepository;

	public List<LckRankingGetDto> getLckRanking() {
		List<LckRanking> lckRankings = lckRankingRepository.getAllByOrderByLckRankingAsc();

		return lckRankings.stream()
				.map(lckRanking -> LckRankingGetDto.of(lckRanking, calculateWinningRate(lckRanking)))
				.collect(Collectors.toList());
	}

	private int calculateWinningRate(LckRanking lckRanking) {
		int totalGames = lckRanking.getTeamWin() + lckRanking.getTeamDefeat();
		return totalGames > 0 ? (lckRanking.getTeamWin() * 100) / totalGames : 0;
	}
}
