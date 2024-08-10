package com.wable.www.WableServer.api.notification.controller;

import com.wable.www.WableServer.api.notification.service.InfoNotificationQueryService;
import com.wable.www.WableServer.common.response.ApiResponse;
import com.wable.www.WableServer.common.response.SuccessStatus;
import com.wable.www.WableServer.common.util.MemberUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT Auth")
@Tag(name="정보 노티 관련",description = "Information Notification Api Document")
public class InfoNotificationController {
	private final InfoNotificationQueryService infoNotificationQueryService;

	@GetMapping("notification/info/all")
	@Operation(summary = "정보 노티 목록 조회 API 입니다.",description = "InfoNotiList")
	public ResponseEntity<ApiResponse<Object>> getInfoNotification(Principal principal) {
		Long memberId = MemberUtil.getMemberId(principal);
		return ApiResponse.success(SuccessStatus.INFO_NOTIFICATION_ALL_SUCCESS,
				infoNotificationQueryService.getInfoNotification(memberId));
	}
}
