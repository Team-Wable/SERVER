package com.wable.www.WableServer.api.notification.service;

import com.wable.www.WableServer.api.notification.domain.News;
import com.wable.www.WableServer.api.notification.dto.response.NewsAllResponseDto;
import com.wable.www.WableServer.api.notification.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsQueryService {
	private static final int NEWS_DEFAULT_PAGE_SIZE = 15;

	private final NewsRepository newsRepository;

	public List<NewsAllResponseDto> getNews(Long cursor) {
		PageRequest pageRequest = PageRequest.of(0, NEWS_DEFAULT_PAGE_SIZE);
		Slice<News> newsSlice;

		if (cursor == -1) {
			newsSlice = newsRepository.findTop15ByOrderByCreatedAtDesc(pageRequest);
		} else {
			newsSlice = newsRepository.findNewsNextPage(cursor, pageRequest);
		}
		return newsSlice.stream()
				.map(NewsAllResponseDto::of).collect(Collectors.toList());
	}
}
