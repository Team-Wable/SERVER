package com.wable.www.WableServer.api.notification.controller;

import com.wable.www.WableServer.api.notification.service.NoticeQueryService;
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
@Tag(name="공지사항 탭",description = "Notice Api Document")
public class NoticeController {
	private final NoticeQueryService noticeQueryService;

	@GetMapping("information/notice")
	@Operation(summary = "공지사항 목록 조회 API 입니다.",description = "NoticeList")
	public ResponseEntity<ApiResponse<Object>> getNotices(@RequestParam(value = "cursor", defaultValue = "-1") Long cursor) {
		return ApiResponse.success(SuccessStatus.NOTICE_ALL_SUCCESS,
				noticeQueryService.getNotices(cursor));
	}
}
