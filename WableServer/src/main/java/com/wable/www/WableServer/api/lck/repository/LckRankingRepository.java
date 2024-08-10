package com.wable.www.WableServer.api.lck.repository;

import com.wable.www.WableServer.api.lck.domain.LckRanking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LckRankingRepository extends JpaRepository<LckRanking, Long> {
	List<LckRanking> getAllByOrderByLckRankingAsc();
}
