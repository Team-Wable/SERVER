package com.wable.www.WableServer.api.viewit.repository;

import com.wable.www.WableServer.api.viewit.domain.Viewit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViewitRepository extends JpaRepository<Viewit, Long> {
	Viewit findViewitById(Long viewitId);

	List<Viewit> findAllByMemberId(Long memberId);

	@Query("SELECT c FROM Viewit c WHERE c.id < :lastViewitId ORDER BY c.createdAt DESC")
	Slice<Viewit> findViewitsNextPage(Long lastViewitId, PageRequest pageRequest);

	Slice<Viewit> findTop15ByOrderByCreatedAtDesc(PageRequest pageRequest);
}
