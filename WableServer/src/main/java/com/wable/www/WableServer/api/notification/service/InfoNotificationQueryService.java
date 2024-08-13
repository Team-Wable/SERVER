package com.wable.www.WableServer.api.notification.service;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.api.notification.domain.InfoNotification;
import com.wable.www.WableServer.api.notification.dto.response.InfoNotificationAllResponseDto;
import com.wable.www.WableServer.api.notification.repository.InfoNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfoNotificationQueryService {
	private static final Logger log = LoggerFactory.getLogger(InfoNotificationQueryService.class);

	@Value("${aws-property.s3-info-image-url}")
	private String INFO_IMAGE_S3;

	private final InfoNotificationRepository infoNotificationRepository;
	private final MemberRepository memberRepository;
	public List<InfoNotificationAllResponseDto> getInfoNotification(Long memberId) {
		Member member = memberRepository.findMemberByIdOrThrow(memberId);
		List<InfoNotification> infoNotifications =
				infoNotificationRepository.findAllByInfoNotificationTargetMemberOrderByCreatedAtDesc(member);

		return infoNotifications.stream()
				.map(infoNotification -> InfoNotificationAllResponseDto.of(
						infoNotification,
						INFO_IMAGE_S3
				)).collect(Collectors.toList());
	}

}
