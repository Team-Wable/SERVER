package com.wable.www.WableServer.api.notification.dto.response;

import com.wable.www.WableServer.api.notification.domain.News;
import com.wable.www.WableServer.common.util.TimeUtilCustom;

public record NewsAllResponseDto(
		Long newsId,
		String newsTitle,
		String newsText,
		String newsImage,
		String time
) {
	public static NewsAllResponseDto of(News news) {
		return new NewsAllResponseDto(
				news.getId(),
				news.getNewsTitle(),
				news.getNewsText(),
				news.getNewsImage(),
				TimeUtilCustom.refineTime(news.getCreatedAt())
		);
	}
}
