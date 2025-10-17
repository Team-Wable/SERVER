package com.wable.www.WableServer.api.curation.controller;

import com.wable.www.WableServer.api.curation.dto.response.CurationGetAllResponseDto;
import com.wable.www.WableServer.api.curation.service.CurationQueryService;
import com.wable.www.WableServer.common.response.ApiResponse;
import com.wable.www.WableServer.common.util.MemberUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static com.wable.www.WableServer.common.response.SuccessStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/")
@SecurityRequirement(name = "JWT Auth")
@Tag(name="큐레이션 관련", description = "Curation API Document")
public class CurationController {
	private final CurationQueryService curationQueryService;

	@GetMapping("v1/curation")
	@Operation(summary = "큐레이션 목록 조회 API 입니다.",description = "Curation List Get")
	public ResponseEntity<ApiResponse<List<CurationGetAllResponseDto>>> getCurationAll(Principal principal,
																					   @RequestParam(value = "cursor") Long cursor) {
		return ApiResponse.success(GET_CURATION_ALL_SUCCESS, curationQueryService.getCurationAll(MemberUtil.getMemberId(principal), cursor));
	}
}
