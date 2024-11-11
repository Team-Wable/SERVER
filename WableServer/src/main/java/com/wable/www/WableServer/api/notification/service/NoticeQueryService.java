package com.wable.www.WableServer.api.notification.service;

import com.wable.www.WableServer.api.notification.domain.Notice;
import com.wable.www.WableServer.api.notification.dto.response.NoticeAllResponseDto;
import com.wable.www.WableServer.api.notification.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeQueryService {
	private static final int NOTICE_DEFAULT_PAGE_SIZE = 15;

	private final NoticeRepository noticeRepository;

	public List<NoticeAllResponseDto> getNotices(Long cursor) {
		PageRequest pageRequest =  PageRequest.of(0, NOTICE_DEFAULT_PAGE_SIZE);
		Slice<Notice> noticeSlice;

		if (cursor == -1) {
			noticeSlice = noticeRepository.findTop15ByOrderByCreatedAtDesc(pageRequest);
		} else {
			noticeSlice = noticeRepository.findNoticeNextPage(cursor, pageRequest);
		}
		return noticeSlice.stream()
				.map(NoticeAllResponseDto::of).collect(Collectors.toList());
	}
}
