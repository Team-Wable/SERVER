package com.wable.www.WableServer.api.notification.controller;

import com.wable.www.WableServer.api.member.service.MemberCommandService;
import com.wable.www.WableServer.api.notification.dto.response.NotificaitonCountResponseDto;
import com.wable.www.WableServer.api.notification.service.NotificationCommandService;
import com.wable.www.WableServer.api.notification.service.NotificationQueryService;
import com.wable.www.WableServer.common.response.ApiResponse;
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
@Tag(name="노티 관련",description = "Notification Api Document")
public class NotificationController {
    private final NotificationCommandService notificationCommandService;
    private final NotificationQueryService notificationQueryService;
    private final MemberCommandService memberCommandService;

    @PatchMapping("notification-check")
    @Operation(summary = "노티 체크 API 입니다.",description = "NotificationCheck")
    public ResponseEntity<ApiResponse<Object>> readNotification(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);
        notificationCommandService.readNotification(memberId);
        return ApiResponse.success(READ_NOTIFICATION_SUCCESS);
    }

    @GetMapping("notification/number")
    @Operation(summary = "노티 개수 반환 API 입니다.",description = "NotificationNumber")
    public ResponseEntity<ApiResponse<NotificaitonCountResponseDto>> countNotification(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponse.success(COUNT_NOTIFICATION_SUCCESS, notificationQueryService.countUnreadNotification(memberId));
    }

    @GetMapping("notification-all")    //페이지네이션 적용 후 지우기
    @Operation(summary = "노티 전체 리스트 조회 API 입니다.",description = "NotificationGetAll")
    public ResponseEntity<ApiResponse<Object>> getNotification(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponse.success(NOTIFICATION_ALL_SUCCESS, notificationQueryService.getNotificationAll(memberId));
    }

    @GetMapping("member-notifications")
    @Operation(summary = "페이지네이션이 적용된 노티 전체 리스트 조회 API 입니다.",description = "NotificationGetPagination")
    public ResponseEntity<ApiResponse<Object>> getNotificationAllPagination(Principal principal,@RequestParam(value = "cursor") Long cursor) {
        Long memberId = MemberUtil.getMemberId(principal);
        memberCommandService.resetMemberFcmBadge(memberId);
        return ApiResponse.success(NOTIFICATION_ALL_SUCCESS, notificationQueryService.getNotificationAllPagination(memberId, cursor));
    }

    @GetMapping("notifications")
    @Operation(summary = "노티 전체 리스트 조회 ver3 API 입니다.",description = "GetNotificationsVer3")
    public ResponseEntity<ApiResponse<Object>> getNotifications(Principal principal,@RequestParam(value = "cursor") Long cursor) {
        Long memberId = MemberUtil.getMemberId(principal);
        memberCommandService.resetMemberFcmBadge(memberId);
        return ApiResponse.success(NOTIFICATION_ALL_SUCCESS, notificationQueryService.getNotifications(memberId, cursor));
    }
}
