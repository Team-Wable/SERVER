package com.wable.www.WableServer.api.notification.service;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.api.notification.domain.InfoNotification;
import com.wable.www.WableServer.api.notification.domain.InfoNotificationType;
import com.wable.www.WableServer.api.notification.domain.Notification;
import com.wable.www.WableServer.api.notification.repository.InfoNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InfoNotificationCommandService {
	private final MemberRepository memberRepository;
	private final InfoNotificationRepository infoNotificationRepository;

	public void postGameStartInfoNotification() {
		List<Member> members = memberRepository.findAllActiveMembers();

		for (Member member : members) {
			InfoNotification gameStartInfoNotification = InfoNotification.builder()
					.infoNotificationTargetMember(member)
					.infoNotificationType(InfoNotificationType.GAMESTART)
					.build();
			infoNotificationRepository.save(gameStartInfoNotification);
		}
	}

	public void postGameDoneInfoNotification() {
		List<Member> members = memberRepository.findAllActiveMembers();

		for (Member member : members) {
			InfoNotification gameDoneInfoNotification = InfoNotification.builder()
					.infoNotificationTargetMember(member)
					.infoNotificationType(InfoNotificationType.GAMEDONE)
					.build();
			infoNotificationRepository.save(gameDoneInfoNotification);
		}
	}

	public void postWeekDoneInfoNotification() {
		List<Member> members = memberRepository.findAllActiveMembers();

		for (Member member : members) {
			InfoNotification weekDoneInfoNotification = InfoNotification.builder()
					.infoNotificationTargetMember(member)
					.infoNotificationType(InfoNotificationType.WEEKDONE)
					.build();
			infoNotificationRepository.save(weekDoneInfoNotification);
		}
	}
}
