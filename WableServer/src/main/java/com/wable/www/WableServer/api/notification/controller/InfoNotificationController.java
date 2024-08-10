package com.wable.www.WableServer.api.notification.controller;

import com.wable.www.WableServer.api.comment.dto.request.CommentPostRequestDto;
import com.wable.www.WableServer.api.notification.service.InfoNotificationCommandService;
import com.wable.www.WableServer.api.notification.service.InfoNotificationQueryService;
import com.wable.www.WableServer.common.response.ApiResponse;
import com.wable.www.WableServer.common.response.SuccessStatus;
import com.wable.www.WableServer.common.util.MemberUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.wable.www.WableServer.common.response.SuccessStatus.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT Auth")
@Tag(name="정보 노티 관련",description = "Information Notification Api Document")
public class InfoNotificationController {
	private final InfoNotificationQueryService infoNotificationQueryService;
	private final InfoNotificationCommandService infoNotificationCommandService;

	@GetMapping("notification/info/all")
	@Operation(summary = "정보 노티 목록 조회 API 입니다.",description = "InfoNotiList")
	public ResponseEntity<ApiResponse<Object>> getInfoNotification(Principal principal) {
		Long memberId = MemberUtil.getMemberId(principal);
		return ApiResponse.success(SuccessStatus.INFO_NOTIFICATION_ALL_SUCCESS,
				infoNotificationQueryService.getInfoNotification(memberId));
	}

	@PostMapping("notification/info/manage/gamedone")
	@Operation(summary = "XX운영용 gamedone 정보 노티 발생.XX", description = "XXXXXXX")
	public ResponseEntity<ApiResponse<Object>> postGameDoneInfoNotification() {
		infoNotificationCommandService.postGameDoneInfoNotification();
		return ApiResponse.success(POST_GAMEDONE_INFONOTIFICATION_SUCCESS);
	}

	@PostMapping("notification/info/manage/gamestart")
	@Operation(summary = "XX운영용 gamestart 정보 노티 발생.XX", description = "XXXXXXX")
	public ResponseEntity<ApiResponse<Object>> postGameStartInfoNotification() {
		infoNotificationCommandService.postGameStartInfoNotification();
		return ApiResponse.success(POST_GAMESTART_INFONOTIFICATION_SUCCESS);
	}

	@PostMapping("notification/info/manage/weekdone")
	@Operation(summary = "XX운영용 weekdone 정보 노티 발생.XX", description = "XXXXXXX")
	public ResponseEntity<ApiResponse<Object>> postWeekDoneInfoNotification() {
		infoNotificationCommandService.postWeekDoneInfoNotification();
		return ApiResponse.success(POST_WEEKDONE_INFONOTIFICATION_SUCCESS);
	}
}
