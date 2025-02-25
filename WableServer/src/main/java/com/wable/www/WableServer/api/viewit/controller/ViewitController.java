package com.wable.www.WableServer.api.viewit.controller;

import com.wable.www.WableServer.api.viewit.dto.request.ViewitPostRequestDto;
import com.wable.www.WableServer.api.viewit.service.ViewitCommandService;
import com.wable.www.WableServer.common.response.ApiResponse;
import com.wable.www.WableServer.common.response.SuccessStatus;
import com.wable.www.WableServer.common.util.MemberUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.wable.www.WableServer.common.response.SuccessStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/")
@SecurityRequirement(name = "JWT Auth")
@Tag(name="뷰잇 관련", description = "Viewit API Document")
public class ViewitController {
	private final ViewitCommandService viewitCommandService;

	@PostMapping("v1/viewit")
	@Operation(summary = "뷰잇 작성 API 입니다.",description = "Viewit Post")
	public ResponseEntity<ApiResponse<Object>> postViewit(Principal principal, ViewitPostRequestDto viewitPostRequestDto) {
		viewitCommandService.postViewit(MemberUtil.getMemberId(principal), viewitPostRequestDto);
		return ApiResponse.success(POST_VIEWIT_SUCCESS);
	}
}
