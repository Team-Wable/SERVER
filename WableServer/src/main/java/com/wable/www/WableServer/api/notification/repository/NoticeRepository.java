package com.wable.www.WableServer.api.notification.repository;

import com.wable.www.WableServer.api.notification.domain.Notice;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
	Slice<Notice> findTop15ByOrderByCreatedAtDesc(PageRequest pageRequest);

	@Query("SELECT c FROM Notice c WHERE c.id < :cursor ORDER BY c.createdAt DESC")
	Slice<Notice> findNoticeNextPage(Long cursor, PageRequest pageRequest);
}
