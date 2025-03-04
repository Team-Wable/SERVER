package com.wable.www.WableServer.api.viewit.controller;

import com.wable.www.WableServer.api.viewit.dto.request.ViewitPostRequestDto;
import com.wable.www.WableServer.api.viewit.dto.response.ViewitGetAllResponseDto;
import com.wable.www.WableServer.api.viewit.service.ViewitCommandService;
import com.wable.www.WableServer.api.viewit.service.ViewitQueryService;
import com.wable.www.WableServer.common.response.ApiResponse;
import com.wable.www.WableServer.common.response.SuccessStatus;
import com.wable.www.WableServer.common.util.MemberUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.wable.www.WableServer.common.response.SuccessStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/")
@SecurityRequirement(name = "JWT Auth")
@Tag(name="뷰잇 관련", description = "Viewit API Document")
public class ViewitController {
	private final ViewitCommandService viewitCommandService;
	private final ViewitQueryService viewitQueryService;

	@PostMapping("v1/viewit")
	@Operation(summary = "뷰잇 작성 API 입니다.",description = "Viewit Post")
	public ResponseEntity<ApiResponse<Object>> postViewit(Principal principal, @RequestBody ViewitPostRequestDto viewitPostRequestDto) {
		viewitCommandService.postViewit(MemberUtil.getMemberId(principal), viewitPostRequestDto);
		return ApiResponse.success(POST_VIEWIT_SUCCESS);
	}

	@PostMapping("v1/viewit/{viewitId}/liked")
	@Operation(summary = "뷰잇 좋아요 API 입니다.",description = "Viewit Like")
	public ResponseEntity<ApiResponse<Object>> liekViewit(Principal principal, @PathVariable("viewitId") Long viewitId) {
		viewitCommandService.likeViewit(MemberUtil.getMemberId(principal), viewitId);
		return ApiResponse.success(LIKE_VIEWIT_SUCCESS);
	}

	@DeleteMapping("v1/viewit/{viewitId}/unliked")
	@Operation(summary = "뷰잇 좋아요 취소 API 입니다.",description = "Viewit Unike")
	public ResponseEntity<ApiResponse<Object>> unlikeViewit(Principal principal, @PathVariable("viewtId") Long viewitId) {
		viewitCommandService.unlikeViewit(MemberUtil.getMemberId(principal),viewitId);
		return ApiResponse.success(UNLIKE_VIEWIT_SUCCESS);
	}

	@DeleteMapping("v1/viewit/{viewitId}")
	@Operation(summary = "뷰잇 삭제 API 입니다.",description = "Viewit Delete")
	public ResponseEntity<ApiResponse<Object>> deleteViewit(Principal principal, @PathVariable("viewitId") Long viewitId) {
		viewitCommandService.deleteViewit(MemberUtil.getMemberId(principal), viewitId);
		return ApiResponse.success(DELETE_VIEWIT_SUCCESS);
	}

	@GetMapping("v1/viewit")
	@Operation(summary = "뷰잇 목록 조회 API 입니다.",description = "Viewit List Get")
	public ResponseEntity<ApiResponse<List<ViewitGetAllResponseDto>>> getViewitAll(Principal principal,
																				   @RequestParam(value = "cursor") Long cursor) {
		return ApiResponse.success(GET_VIEW_ALL_SUCCESS, viewitQueryService.getViewitAll(MemberUtil.getMemberId(principal), cursor));
	}

}
