package com.wable.www.WableServer.api.notification.dto.response;

import com.wable.www.WableServer.api.notification.domain.Notice;
import com.wable.www.WableServer.common.util.TimeUtilCustom;

public record NoticeAllResponseDto(
		Long noticeId,
		String noticeTitle,
		String noticeText,
		String noticeImage,
		String time
) {
	public static NewsAllResponseDto of(Notice notice) {
		return new NewsAllResponseDto(
				notice.getId(),
				notice.getNoticeTitle(),
				notice.getNoticeText(),
				notice.getNoticeImage(),
				TimeUtilCustom.refineTime(notice.getCreatedAt())
		);
	}
}
