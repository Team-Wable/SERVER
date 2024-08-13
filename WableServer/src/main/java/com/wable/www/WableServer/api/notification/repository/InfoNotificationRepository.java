package com.wable.www.WableServer.api.notification.repository;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.notification.domain.InfoNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InfoNotificationRepository extends JpaRepository<InfoNotification, Long> {
	List<InfoNotification> findAllByInfoNotificationTargetMemberOrderByCreatedAtDesc(Member member);
}
