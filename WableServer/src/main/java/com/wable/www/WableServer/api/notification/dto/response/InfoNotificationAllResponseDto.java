package com.wable.www.WableServer.api.notification.dto.response;

import com.wable.www.WableServer.api.notification.domain.InfoNotification;
import com.wable.www.WableServer.common.util.TimeUtilCustom;

public record InfoNotificationAllResponseDto(
		String InfoNotificationType,
		String time,
		String imageUrl
) {
	public static InfoNotificationAllResponseDto of(InfoNotification infoNotification, String imageUrl) {
		return new InfoNotificationAllResponseDto(
				infoNotification.getInfoNotificationType().name(),
				TimeUtilCustom.refineTime(infoNotification.getCreatedAt()),
				imageUrl
		);
	}
}
