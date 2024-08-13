package com.wable.www.WableServer.common.crontab;

import com.wable.www.WableServer.api.ghost.domain.Ghost;
import com.wable.www.WableServer.api.ghost.repository.GhostDbRepository;
import com.wable.www.WableServer.api.ghost.repository.GhostRepository;
import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.api.notification.domain.Notification;
import com.wable.www.WableServer.api.notification.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class GhostScheduler {

    private final GhostDbRepository ghostDbRepository;
    private final NotificationRepository notificationRepository;


    public GhostScheduler(GhostDbRepository ghostDbRepository,
                          NotificationRepository notificationRepository) {
        this.ghostDbRepository = ghostDbRepository;
        this.notificationRepository = notificationRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 00:00에 실행
    @Transactional
    public void updateGhostStatus() {
        LocalDate fiveDaysAgo = LocalDate.now().minusDays(5);
        // 5일 전 이전에 생성된 Ghost 엔터티 중 isRecovered가 false인 것들을 찾아 업데이트
        List<Ghost> ghostsToUpdate = ghostDbRepository.findGhostsToUpdate(fiveDaysAgo);
        for (Ghost ghost : ghostsToUpdate) {
            ghostDbRepository.updateGhostStatus(fiveDaysAgo);

            Member member = ghost.getGhostTargetMember();

            if(member.getMemberGhost() == -85) {
                Notification actingContinueNotification = Notification.builder()
                        .notificationTargetMember(member)
                        .notificationTriggerMemberId(-1L)
                        .notificationTriggerType("actingContinue")
                        .notificationTriggerId(null)
                        .isNotificationChecked(false)
                        .notificationText("")
                        .build();
                Notification savedActingContinueNotification = notificationRepository.save(actingContinueNotification);
            }

            ghostDbRepository.incrementMemberGhostCount(ghost.getGhostTargetMember());
        }
    }
}
