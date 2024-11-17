package com.wable.www.WableServer.api.notification.repository;

import com.wable.www.WableServer.api.notification.domain.News;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NewsRepository extends JpaRepository<News, Long> {
	Slice<News> findTop15ByOrderByCreatedAtDesc(PageRequest pageRequest);

	@Query("SELECT c FROM News c WHERE c.id < :cursor ORDER BY c.createdAt DESC")
	Slice<News> findNewsNextPage(Long cursor, PageRequest pageRequest);
}
