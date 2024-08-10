package com.wable.www.WableServer.api.lck.controller;

import com.wable.www.WableServer.api.lck.service.LckScheduleQueryService;
import com.wable.www.WableServer.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.wable.www.WableServer.common.response.SuccessStatus.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT Auth")
@Tag(name="LCk Schedule 관련",description = "LCK Schedule Api Document")
public class LckScheduleController {
	private final LckScheduleQueryService lckScheduleQueryService;

	@GetMapping("v1/information/schedule")
	@Operation(summary = "Lck Schedule API입니다.", description = "ScheduleGet")
	public ResponseEntity<ApiResponse<Object>> getLckSchedule() {
		return ApiResponse.success(GET_LCK_SCHEDULE_SUCCESS, lckScheduleQueryService.getLckSchedule());
	}
}
