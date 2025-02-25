package com.wable.www.WableServer.api.notification.controller;

import com.wable.www.WableServer.api.notification.service.NewsQueryService;
import com.wable.www.WableServer.common.response.ApiResponse;
import com.wable.www.WableServer.common.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT Auth")
@Tag(name="뉴스탭",description = "News Api Document")
public class NewsController {
	private final NewsQueryService newsQueryService;

	@GetMapping("information/news")
	@Operation(summary = "뉴스 목록 조회 API 입니다.",description = "NewsList")
	public ResponseEntity<ApiResponse<Object>> getNews(@RequestParam(value = "cursor", defaultValue = "-1") Long cursor) {
		return ApiResponse.success(SuccessStatus.NEWS_ALL_SUCCESS,
				newsQueryService.getNews(cursor));
	}
}
