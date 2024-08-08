package com.wable.www.WableServer.api.notification.service;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.api.notification.domain.Notification;
import com.wable.www.WableServer.api.notification.dto.response.NotificaitonCountResponseDto;
import com.wable.www.WableServer.api.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationCommandService {
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;
    @Transactional
    public void readNotification(Long memberId) {
        Member targetMember = memberRepository.findMemberByIdOrThrow(memberId);
        List<Notification> unreadNotifications = notificationRepository.findAllByNotificationTargetMemberAndIsNotificationChecked(targetMember,false);

        unreadNotifications.forEach(Notification::readNotification);
    }
}
