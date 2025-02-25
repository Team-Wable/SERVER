package com.wable.www.WableServer.api.viewit.repository;

import com.wable.www.WableServer.api.viewit.domain.ViewitLiked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewitLikedRepository extends JpaRepository<ViewitLiked, Long> {
	boolean existsByViewitIdAndMemberId(Long viewitId, Long memberId);
}
