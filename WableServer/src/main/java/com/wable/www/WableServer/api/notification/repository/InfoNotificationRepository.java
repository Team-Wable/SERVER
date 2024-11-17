package com.wable.www.WableServer.api.notification.repository;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.notification.domain.InfoNotification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InfoNotificationRepository extends JpaRepository<InfoNotification, Long> {
	List<InfoNotification> findAllByInfoNotificationTargetMemberOrderByCreatedAtDesc(Member member);

	Slice<InfoNotification> findTop15ByInfoNotificationTargetMemberOrderByCreatedAtDesc(Member member, PageRequest pageRequest);

//	Slice<InfoNotification> findInfoNotificationsNextPage(Long cursor, Long memberId, PageRequest pageRequest);

	@Query("SELECT i FROM InfoNotification i WHERE i.id < ?1 AND i.infoNotificationTargetMember.id = ?2 ORDER BY i.createdAt DESC")
	Slice<InfoNotification> findInfoNotificationsNextPage(Long lastNotificationId, Long memberId, PageRequest pageRequest);
}
