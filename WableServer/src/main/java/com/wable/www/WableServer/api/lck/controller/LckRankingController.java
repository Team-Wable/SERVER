package com.wable.www.WableServer.api.lck.controller;

import com.wable.www.WableServer.api.lck.service.LckRankingQueryService;
import com.wable.www.WableServer.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.wable.www.WableServer.common.response.SuccessStatus.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT Auth")
@Tag(name="LCK Ranking 관련",description = "LCK Ranking Api Document")
public class LckRankingController {
	private final LckRankingQueryService lckRankingQueryService;

	@GetMapping("v1/information/rank")
	@Operation(summary = "Lck Ranking API입니다.", description = "RankingGet")
	public ResponseEntity<ApiResponse<Object>> getLckRanking(Principal principal) {
		return ApiResponse.success(GET_LCK_RANKING_SUCCESS, lckRankingQueryService.getLckRanking());

	}

}
