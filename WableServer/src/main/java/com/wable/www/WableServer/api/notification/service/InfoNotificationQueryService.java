package com.wable.www.WableServer.api.notification.service;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.api.notification.domain.InfoNotification;
import com.wable.www.WableServer.api.notification.dto.response.InfoNotificationAllResponseDto;
import com.wable.www.WableServer.api.notification.repository.InfoNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfoNotificationQueryService {
	@Value("${aws-property.s3-info-image-url}")
	private String INFO_IMAGE_S3;

	private static final int NOTIFICATION_DEFAULT_PAGE_SIZE = 15;

	private final InfoNotificationRepository infoNotificationRepository;
	private final MemberRepository memberRepository;

	public List<InfoNotificationAllResponseDto> getInfoNotifications(Long memberId, Long cursor) {
		Member member = memberRepository.findMemberByIdOrThrow(memberId);
		PageRequest pageRequest = PageRequest.of(0, NOTIFICATION_DEFAULT_PAGE_SIZE);
		Slice<InfoNotification> infoNotificationSlice;

		if (cursor == -1) {
			infoNotificationSlice = infoNotificationRepository.findTop15ByInfoNotificationTargetMemberOrderByCreatedAtDesc(member, pageRequest);
		} else {
			infoNotificationSlice = infoNotificationRepository.findInfoNotificationsNextPage(cursor, memberId, pageRequest);
		}

		return infoNotificationSlice.stream()
				.map(infoNotification -> InfoNotificationAllResponseDto.of(
						infoNotification,
						INFO_IMAGE_S3
				)).collect(Collectors.toList());
	}

}
