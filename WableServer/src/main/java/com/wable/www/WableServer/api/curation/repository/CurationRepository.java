package com.wable.www.WableServer.api.curation.repository;

import com.wable.www.WableServer.api.curation.domain.Curation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CurationRepository extends JpaRepository<Curation, Long> {
	Curation findCurationById(Long curaitonId);

	@Query("SELECT c FROM Curation c WHERE c.id < :lastCurationId ORDER BY c.createdAt DESC")
	Slice<Curation> findCurationsByNextPage(Long lastCurationId, PageRequest pageRequest);

	Slice<Curation> findTop15ByOrderByCreatedAtDesc(PageRequest pageRequest);
}
