package com.wable.www.WableServer.api.viewit.service;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.api.notification.domain.Notification;
import com.wable.www.WableServer.api.notification.repository.NotificationRepository;
import com.wable.www.WableServer.api.viewit.domain.Viewit;
import com.wable.www.WableServer.api.viewit.domain.ViewitLiked;
import com.wable.www.WableServer.api.viewit.dto.request.ViewitPostRequestDto;
import com.wable.www.WableServer.api.viewit.repository.ViewitLikedRepository;
import com.wable.www.WableServer.api.viewit.repository.ViewitRepository;
import com.wable.www.WableServer.common.exception.BadRequestException;
import com.wable.www.WableServer.common.exception.UnAuthorizedException;
import com.wable.www.WableServer.common.response.ErrorStatus;
import com.wable.www.WableServer.external.fcm.dto.FcmMessageDto;
import com.wable.www.WableServer.external.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ViewitCommandService {
	private final MemberRepository memberRepository;
	private final ViewitRepository viewitRepository;
	private final ViewitLikedRepository viewitLikedRepository;
	private final FcmService fcmService;
	private final NotificationRepository notificationRepository;

	public void postViewit(Long memberId, ViewitPostRequestDto viewitPostRequestDto) {
		Member member = memberRepository.findMemberByIdOrThrow(memberId);

		member.increaseExpPostContent();

		Viewit viewit = viewitRepository.save(Viewit.builder()
				.memberId(memberId)
				.viewitImage(viewitPostRequestDto.viewitImage())
				.viewitLink(viewitPostRequestDto.viewitLink())
				.viewitTitle(viewitPostRequestDto.viewitTitle())
				.viewitText(viewitPostRequestDto.viewitText())
				.viewitName(viewitPostRequestDto.viewitName())
				.build());
	}

	public void likeViewit(Long memberId, Long viewitId) {
		Member triggerMember = memberRepository.findMemberByIdOrThrow(memberId);
		Viewit viewit = viewitRepository.findViewitById(viewitId);

		triggerMember.increaseExpPostLike();

		isDuplicateViewitLike(memberId, viewitId);

		Member targetMember = memberRepository.findMemberByIdOrThrow(viewit.getMemberId());
		ViewitLiked viewitLiked = ViewitLiked.builder()
				.memberId(memberId)
				.viewitId(viewitId)
				.build();
		viewitLikedRepository.save(viewitLiked);

		if (triggerMember != targetMember) {  //자신 게시물에 대한 좋아요 누르면 알림 발생 x
			Notification notification = Notification.builder()
					.notificationTargetMember(targetMember)
					.notificationTriggerMemberId(triggerMember.getId())
					.notificationTriggerType("ViewitLiked")
					.notificationTriggerId(viewitId)
					.isNotificationChecked(false)
					.notificationText("")
					.build();
			Notification savedNotification = notificationRepository.save(notification);


			if (Boolean.TRUE.equals(targetMember.getIsPushAlarmAllowed())) {
				String FcmMessageTitle = triggerMember.getNickname() + "님이 " + targetMember.getNickname() + "님의 뷰잇을 좋아합니다.";
				targetMember.increaseFcmBadge();
				FcmMessageDto contentLikeFcmMessage = FcmMessageDto.builder()
						.validateOnly(false)
						.message(FcmMessageDto.Message.builder()
								.notificationDetails(FcmMessageDto.NotificationDetails.builder()
										.title(FcmMessageTitle)
										.body("")
										.build())
								.token(targetMember.getFcmToken())
								.data(FcmMessageDto.Data.builder()
										.name("viewitLike")
										.description("뷰잇 좋아요 푸시 알림")
										.relateContentId(String.valueOf(viewitId))
										.build())
								.badge(targetMember.getFcmBadge())
								.build())
						.build();

				fcmService.sendMessage(contentLikeFcmMessage);
			}
		}
	}

	public void unlikeViewit(Long memberId, Long viewitId) {
		if(!viewitLikedRepository.existsByViewitIdAndMemberId(viewitId,memberId)) {
			throw new BadRequestException(ErrorStatus.UNEXITST_VIEWIT_LIKE.getMessage());
		}

		viewitLikedRepository.deleteByMemberIdAndViewitId(memberId,viewitId);

		Member targetMember = memberRepository.findMemberByIdOrThrow(viewitRepository.findViewitById(viewitId).getMemberId());

		notificationRepository.deleteByNotificationTargetMemberAndNotificationTriggerMemberIdAndNotificationTriggerTypeAndNotificationTriggerId(
				targetMember, memberId, "viewitLiked", viewitId);
	}

	public void deleteViewit(Long memberId, Long viewitId) {
		deleteValidate(memberId,viewitId);
		notificationRepository.deleteByNotificationTriggerTypeAndNotificationTriggerId("viewitLiked",viewitId);
		viewitRepository.deleteById(viewitId);
	}

	private void deleteValidate(Long memberId, Long viewitId) {
		Viewit viewit = viewitRepository.findViewitById(viewitId);
		Long viewitMemberId = viewit.getMemberId();

		if(!viewitMemberId.equals(memberId)) {
			throw new UnAuthorizedException(ErrorStatus.UNAUTHORIZED_MEMBER.getMessage());
		}
	}

	private void isDuplicateViewitLike(Long memberId, Long viewitId) {
		if(viewitLikedRepository.existsByViewitIdAndMemberId(viewitId, memberId)) {
			throw new BadRequestException(ErrorStatus.DUPLICATION_VIEWIT_LIKE.getMessage());
		}
	}
}
