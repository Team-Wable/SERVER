package com.wable.www.WableServer.api.lck.controller;

import com.wable.www.WableServer.api.lck.service.LckGameTypeQueryService;
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
@Tag(name="LCK Game Type 관련",description = "LCK Game Type Api Document")
public class LckGameTypeController {
	private LckGameTypeQueryService lckGameTypeQueryService;

	@GetMapping("v1/information/gametype")
	@Operation(summary = "Lck Game Type API입니다.", description = "LckGameTypeGet")
	public ResponseEntity<ApiResponse<Object>> getGameType(Principal principal) {
		return ApiResponse.success(GET_LCK_GAMETYPE_SUCCESS, lckGameTypeQueryService.getLckGameType());
	}

}
