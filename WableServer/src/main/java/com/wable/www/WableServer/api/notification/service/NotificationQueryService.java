package com.wable.www.WableServer.api.notification.service;

import com.wable.www.WableServer.api.comment.domain.Comment;
import com.wable.www.WableServer.api.comment.repository.CommentRepository;
import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.api.notification.domain.Notification;
import com.wable.www.WableServer.api.notification.dto.response.NotificaitonCountResponseDto;
import com.wable.www.WableServer.api.notification.dto.response.NotificationAllResponseDto;
import com.wable.www.WableServer.api.notification.dto.response.NotificationAllResponseDtoVer2;
import com.wable.www.WableServer.api.notification.dto.response.NotificationAllResponseDtoVer3;
import com.wable.www.WableServer.api.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NotificationQueryService {
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;
    private final CommentRepository commentRepository;

    @Value("${aws-property.s3-system-image-url}")
    private String SYSTEM_IMAGE_S3;

    @Value("${aws-property.s3-popular-image-url}")
    private String POPULAR_IMAGE_S3;
    private final int NOTIFICATION_DEFAULT_PAGE_SIZE = 15;

    public NotificaitonCountResponseDto countUnreadNotification(Long memberId) {
        Member member = memberRepository.findMemberByIdOrThrow(memberId);
        int number = notificationRepository.countByNotificationTargetMemberAndIsNotificationChecked(member, false);
        return NotificaitonCountResponseDto.of(number);
    }

    public List<NotificationAllResponseDto> getNotificationAll(Long memberId){  //페이지네이션 적용 후 지우기
        Member usingMember = memberRepository.findMemberByIdOrThrow(memberId);
        List<Notification> notificationList = notificationRepository.findNotificationsByNotificationTargetMemberOrderByCreatedAtDesc(usingMember);

        return notificationList.stream()
                .map(oneNotification -> NotificationAllResponseDto.of(
                        usingMember,
                        isSystemOrUser(oneNotification.getNotificationTriggerMemberId(),
                                oneNotification.getNotificationTriggerType()),
                        oneNotification,
                        oneNotification.isNotificationChecked(),
                        refineNotificationTriggerId(oneNotification.getNotificationTriggerType(),
                                oneNotification.getNotificationTriggerId(), oneNotification),
                        profileUrl(oneNotification.getId(), oneNotification.getNotificationTriggerType())
                )).collect(Collectors.toList());
    }

    public List<NotificationAllResponseDtoVer2> getNotificationAllPagination(Long memberId, Long cursor){
        Member usingMember = memberRepository.findMemberByIdOrThrow(memberId);

        PageRequest pageRequest = PageRequest.of(0, NOTIFICATION_DEFAULT_PAGE_SIZE);
        Slice<Notification> notificationList;

        if(cursor==-1){
            notificationList = notificationRepository.findTop15ByNotificationTargetMemberOrderByCreatedAtDesc(usingMember, pageRequest);
        }else{
            notificationList = notificationRepository.findNotificationsNextPage(cursor, memberId, pageRequest);
        }

        return notificationList.stream()
                .map(oneNotification -> NotificationAllResponseDtoVer2.of(
                        usingMember,
                        isSystemOrUser(oneNotification.getNotificationTriggerMemberId(),
                                oneNotification.getNotificationTriggerType()),
                        oneNotification,
                        oneNotification.isNotificationChecked(),
                        refineNotificationTriggerId(oneNotification.getNotificationTriggerType(),
                                oneNotification.getNotificationTriggerId(), oneNotification),
                        profileUrl(oneNotification.getId(), oneNotification.getNotificationTriggerType()),
                        isDeletedMember(oneNotification.getNotificationTriggerMemberId())
                )).collect(Collectors.toList());
    }

    public List<NotificationAllResponseDtoVer3> getNotifications(Long memberId, Long cursor){
        Member usingMember = memberRepository.findMemberByIdOrThrow(memberId);

        PageRequest pageRequest = PageRequest.of(0, NOTIFICATION_DEFAULT_PAGE_SIZE);
        Slice<Notification> notificationList;

        if(cursor==-1){
            notificationList = notificationRepository.findTop15ByNotificationTargetMemberOrderByCreatedAtDesc(usingMember, pageRequest);
        }else{
            notificationList = notificationRepository.findNotificationsNextPage(cursor, memberId, pageRequest);
        }

        return notificationList.stream()
                .map(oneNotification -> NotificationAllResponseDtoVer3.of(
                        usingMember,
                        isSystemOrUser(oneNotification.getNotificationTriggerMemberId(),
                                oneNotification.getNotificationTriggerType()),//발생유저닉네임
                        oneNotification,
                        oneNotification.isNotificationChecked(),
                        refineNotificationTriggerId(oneNotification.getNotificationTriggerType(),
                                oneNotification.getNotificationTriggerId(), oneNotification),
                        profileUrl(oneNotification.getId(), oneNotification.getNotificationTriggerType()),
                        isDeletedMember(oneNotification.getNotificationTriggerMemberId()),
                        refineNotificationTriggerMemberId(oneNotification.getNotificationTriggerMemberId(),
                                oneNotification.getNotificationTriggerType()) // 발생유저아이디
                )).collect(Collectors.toList());
    }

    private long refineNotificationTriggerId (String triggerType, Long triggerId, Notification notification){

//        Comment comment = commentRepository.findCommentByIdOrThrow(triggerId);
        //답글관련(답글좋아요 혹은 답글 작성, 답글 투명도)시 게시물 id 반환
        if(triggerType.equals("comment") || triggerType.equals("commentLiked") || triggerType.equals("commentGhost")) {
            Comment comment = commentRepository.findCommentByIdOrThrow(triggerId);
            return comment.getContent().getId();
        }else{
            if(triggerType.equals("actingContinue")||triggerType.equals("userBan")) {
                return -1;
            }
            return notification.getNotificationTriggerId();}
    }
    //todo 추후에 인기글 -> 불꽃, 정보 -> 확성기, 시스템 -> wable로고, 사용자 -> 프로필 사진
    private String profileUrl(Long notificationId, String triggerType){
        if(triggerType.equals("comment") || triggerType.equals("commentLiked") || triggerType.equals("contentLiked")){
            Notification notification = notificationRepository.findNotificationById(notificationId);
            Member triggerMember = memberRepository.findMemberByIdOrThrow(notification.getNotificationTriggerMemberId());
            return triggerMember.getProfileUrl();
        }
        if(triggerType.equals("popularWriter")||triggerType.equals("popularContent")) {
            return POPULAR_IMAGE_S3;
        }else{
            return SYSTEM_IMAGE_S3;
        }
    }

    //운영 노티의 경우 트리거 유저가 없기 때문에 "System"을 반환하도록 수정
    private String isSystemOrUser(Long memberId, String triggerType) {
        if(memberId != -1) {
            return memberRepository.findMemberByIdOrThrow(memberId).getNickname();
        }
        if(triggerType.equals("commentGhost") || triggerType.equals("contentGhost")) {
            return "System";
        }
        else return "System";
    }

    //탈퇴한 회원인지 아닌지
    private boolean isDeletedMember(Long triggerMemberId){
        if(triggerMemberId == -1L)
        {
            return false;
        }
        else
            return memberRepository.findMemberByIdOrThrow(triggerMemberId).isDeleted();
        //운영 노티인 경우 trigger의 닉네임이 따로 나오지 않아서 별도의 로직 불필요
    }

    private Long refineNotificationTriggerMemberId(Long triggerMemberId, String triggerType) {
        if(triggerType.equals("commentGhost") || triggerType.equals("contentGhost")){
            return -1L;
        }
        else
            return triggerMemberId;
    }
}

