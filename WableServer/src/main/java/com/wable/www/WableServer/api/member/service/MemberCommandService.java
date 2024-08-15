package com.wable.www.WableServer.api.member.service;

import com.wable.www.WableServer.api.ghost.domain.Ghost;
import com.wable.www.WableServer.api.ghost.repository.GhostRepository;
import com.wable.www.WableServer.api.comment.domain.Comment;
import com.wable.www.WableServer.api.comment.domain.CommentLiked;
import com.wable.www.WableServer.api.comment.repository.CommentLikedRepository;
import com.wable.www.WableServer.api.comment.repository.CommentRepository;
import com.wable.www.WableServer.api.content.domain.Content;
import com.wable.www.WableServer.api.content.domain.ContentLiked;
import com.wable.www.WableServer.api.content.repository.ContentLikedRepository;
import com.wable.www.WableServer.api.content.repository.ContentRepository;
import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.dto.request.MemberPatchFcmBadgeRequestDto;
import com.wable.www.WableServer.api.member.dto.request.MemberProfilePatchRequestDto;
import com.wable.www.WableServer.api.member.dto.request.MemberWithdrawalPatchRequestDto;
import com.wable.www.WableServer.api.member.dto.request.ProfilePatchRequestDto;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.api.notification.repository.NotificationRepository;
import com.wable.www.WableServer.api.notification.domain.Notification;
import com.wable.www.WableServer.external.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {
    private final MemberRepository memberRepository;
    private final NotificationRepository notificationRepository;
    private final GhostRepository ghostRepository;
    private final ContentRepository contentRepository;
    private final CommentRepository commentRepository;
    private final CommentLikedRepository commentLikedRepository;
    private final ContentLikedRepository contentLikedRepository;
    private final S3Service s3Service;

    @Value("${aws-property.s3-system-image-url}")
    private String SYSTEM_IMAGE_S3;

    @Value("${aws-property.s3-domain}")
    private String S3_URL;

    public void withdrawalMember(Long memberId, MemberWithdrawalPatchRequestDto memberWithdrawalPatchRequestDto) {
        Member member = memberRepository.findMemberByIdOrThrow(memberId);
        List<Ghost> ghosts = ghostRepository.findByGhostTargetMember(member);

        member.updateNickname("탈퇴한 회원");
        member.updateProfileUrl(SYSTEM_IMAGE_S3);
        member.updateDeletedReason(memberWithdrawalPatchRequestDto.deleted_reason());

//        notificationRepository.deleteBynotificationTargetMember(member);

        List<Content> contentList = contentRepository.findContentByMember(member);
        for(Content content : contentList)  {
            content.softDelete();
        }

        List<Comment> commentList = commentRepository.findAllByMember(member);
        for(Comment comment : commentList)  {
            comment.softDelete();
        }

        List<Notification> notification1 = notificationRepository.findAllByNotificationTargetMember(member);
        List<Notification> notification2 = notificationRepository.findAllByNotificationTriggerMemberId(memberId);

        notificationRepository.deleteAll(notification1);
        notificationRepository.deleteAll(notification2);

        for(Ghost ghost:ghosts){ ghost.softDelete(); }

        member.softDelete();
    }

    public void testWithdrawalMember(Long memberId){
        Member member = memberRepository.findMemberByIdOrThrow(memberId);
        List<Content> contentList = contentRepository.findContentByMember(member);
        List<Ghost> ghostList1 = ghostRepository.findByGhostTargetMember(member);
        List<Ghost> ghostList2 = ghostRepository.findByGhostTriggerMember(member);

        //게시글들 안에서 각 게시글에 대한 답글들의 좋아요 노티, 투명도 노티, 답글 노티, 답글 삭제 + 게시글 좋아요 삭제, 게시글 투명도 삭제, 게시글 삭제(소프트 딜리트 X)
        for(Content content : contentList) {
            Long contentId = content.getId();
            List<Comment> comments = commentRepository.findCommentsByContentId(contentId);
            for(Comment comment : comments) {
                notificationRepository.deleteByNotificationTriggerTypeAndNotificationTriggerId("commentLiked",comment.getId());
                notificationRepository.deleteByNotificationTriggerTypeAndNotificationTriggerId("commentGhost",comment.getId());
                notificationRepository.deleteByNotificationTriggerTypeAndNotificationTriggerId("comment", comment.getId());
            }
            notificationRepository.deleteByNotificationTriggerTypeAndNotificationTriggerId("contentLiked",contentId);
            notificationRepository.deleteByNotificationTriggerTypeAndNotificationTriggerId("contentGhost",contentId);
            contentRepository.deleteById(contentId);
        }

        //투명도 관련 운영 노티는 위에서 삭제되지 않기 때문에 아래 과정을 통해서 삭제되게끔 했습니다.
        //target일 경우랑 trigger일 경우, 두 가지 경우에 대해서 모두 진행한 이유는 혹시 몰라서입니다.
        List<Notification> notification1 = notificationRepository.findAllByNotificationTargetMember(member);
        List<Notification> notification2 = notificationRepository.findAllByNotificationTriggerMemberId(memberId);

        notificationRepository.deleteAll(notification1);
        notificationRepository.deleteAll(notification2);

        //초기 앱심사를 위해서 만든 탈퇴이기 때문에 탈퇴하는 유저가 누른 투명도에 대해 상대방을 회복 시킬지 말지에 대한 부분은 생각하지 않고
        //그냥 삭제되는 것으로 일단 진행하겠습니다. (p.s 이전에 카톡으로 추후에는 soft delete 적용하기로함!)
        //이대로 진행할 경우 어떤 유저는 평생 default값이 -1일 수도ㅜㅜ
        ghostRepository.deleteAll(ghostList1);
        ghostRepository.deleteAll(ghostList2);

        //좋아요 삭제
        List<ContentLiked> contentLiked = contentLikedRepository.findAllByMemberId(memberId);
        List<CommentLiked> commentLiked = commentLikedRepository.findAllByMemberId(memberId);
        contentLikedRepository.deleteAll(contentLiked);
        commentLikedRepository.deleteAll(commentLiked);

        //탈퇴하는 유저가 작성한 답글 삭제
        commentRepository.deleteCommentsByMemberId(memberId);

        memberRepository.delete(member);
    }

    public void updateMemberProfile(Long memberId, MemberProfilePatchRequestDto memberProfilePatchRequestDto) {
        Member existingMember = memberRepository.findMemberByIdOrThrow(memberId);

        // 업데이트할 속성만 복사
        if (memberProfilePatchRequestDto.nickname() != null) {
            existingMember.updateNickname(memberProfilePatchRequestDto.nickname());
        }
        if (memberProfilePatchRequestDto.member_intro() != null) {
            existingMember.updateMemberIntro(memberProfilePatchRequestDto.member_intro());
        }
        if (memberProfilePatchRequestDto.profile_url() != null) {
            existingMember.updateProfileUrl(memberProfilePatchRequestDto.profile_url());
        }
        if (memberProfilePatchRequestDto.is_alarm_allowed() != null) {
            existingMember.updateMemberIsAlarmAllowed(memberProfilePatchRequestDto.is_alarm_allowed());
        }
        // 저장
        memberRepository.save(existingMember);
    }

    public void updateMemberProfile2(Long memberId, MultipartFile multipartFile, ProfilePatchRequestDto profilePatchRequestDto) {
        Member existingMember = memberRepository.findMemberByIdOrThrow(memberId);

        // 업데이트할 속성만 복사
        if (profilePatchRequestDto.nickname() != null) {
            existingMember.updateNickname(profilePatchRequestDto.nickname());
        }
        if (profilePatchRequestDto.memberIntro() != null) {
            existingMember.updateMemberIntro(profilePatchRequestDto.memberIntro());
        }
        //이미지를 받았을 경우 S3에 업로드하고, memberTable값 수정하고, 이전에 올라갔던 이미지는 S3에서 삭제
        //이때 기본 이미지였다면 삭제 과정은 스킵. 현재는 깃허브에 올라간 사진이라서 사라지지 않지만, 추후 기본 이미지를 우리 S3에 올릴 것을 대비.
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String existedImage = existingMember.getProfileUrl();

            try {
                String s3ImageUrl = s3Service.uploadImage(memberId.toString(), multipartFile);

                String existedKey = removeBaseUrl(existedImage, S3_URL);

                s3Service.deleteImage(existedKey);

                existingMember.updateProfileUrl(s3ImageUrl);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        if (profilePatchRequestDto.isAlarmAllowed() != null) {
            existingMember.updateMemberIsAlarmAllowed(profilePatchRequestDto.isAlarmAllowed());
        }
        if (profilePatchRequestDto.isPushAlarmAllowed() != null) {
            existingMember.updateMemberIsPushAlarmAllowed(profilePatchRequestDto.isPushAlarmAllowed());
        }
        if (profilePatchRequestDto.memberFanTeam() != null) {
            existingMember.updateMemberFanTeam(profilePatchRequestDto.memberFanTeam());
        }
        if (profilePatchRequestDto.fcmToken() != null) {
            existingMember.updateMemberFcmToken(profilePatchRequestDto.fcmToken());
        }
		if (profilePatchRequestDto.memberLckYears() != null) {
            existingMember.updateMemberLckYears(profilePatchRequestDto.memberLckYears());
        }
        if (profilePatchRequestDto.memberDefaultProfileImage() != null) {
            existingMember.updateProfileUrl(profilePatchRequestDto.memberDefaultProfileImage());
        }
        memberRepository.save(existingMember);
    }

    public void resetMemberFcmBadge(Long memberId) {
        Member member = memberRepository.findMemberByIdOrThrow(memberId);
        member.resetFcmBadge();
    }

    public void updateMemberFcmBadge(Long memberId, MemberPatchFcmBadgeRequestDto memberPatchFcmBadgeRequestDto) {
        Member member = memberRepository.findMemberByIdOrThrow(memberId);
        member.updateFcmBadge(Math.max(memberPatchFcmBadgeRequestDto.fcmBadge(), 0));
    }

    private static String removeBaseUrl(String fullUrl, String baseUrl) {
        if (fullUrl.startsWith(baseUrl)) {
            return fullUrl.substring(baseUrl.length());
        } else {
            return fullUrl; // baseUrl이 존재하지 않는 경우 전체 URL 반환
        }
    }
}
