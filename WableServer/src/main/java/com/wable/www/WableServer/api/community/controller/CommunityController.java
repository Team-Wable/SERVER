package com.wable.www.WableServer.api.community.controller;

import com.wable.www.WableServer.api.community.dto.request.CommunityPreinRequestDto;
import com.wable.www.WableServer.api.community.dto.response.GetMemberCommunityResponseDto;
import com.wable.www.WableServer.api.community.dto.response.PreinCommunityResponseDto;
import com.wable.www.WableServer.api.community.service.CommunityCommandService;
import com.wable.www.WableServer.api.community.service.CommunityQueryService;
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
@RequiredArgsConstructor
@RequestMapping("api/")
@SecurityRequirement(name = "JWT Auth")
@Tag(name="커뮤니티 관련", description = "Community API Document")
public class CommunityController {
	private final CommunityQueryService communityQueryService;
	private final CommunityCommandService communityCommandService;

	@GetMapping("v1/community/member")
	@Operation(summary = "사용자 참여 커뮤니티 조회 API 입니다.",description = "Member Get Community")
	public ResponseEntity<ApiResponse<GetMemberCommunityResponseDto>> getMemberCommunity(Principal principal) {
		return ApiResponse.success(GET_MEMBER_COMMUNITY_SUCCESS,communityQueryService.getMemberCommunity(MemberUtil.getMemberId(principal)));
	}

	@PatchMapping("v1/community/prein")
	@Operation(summary = "사용자 커뮤니티 사전 참여 API 입니다.",description = "Member Prein Community")
	public ResponseEntity<ApiResponse<Object>> preinCommunity(Principal principal, @RequestBody CommunityPreinRequestDto communityPreinRequestDto) {
		Long memberId = MemberUtil.getMemberId(principal);
		communityCommandService.preinCommunity(memberId, communityPreinRequestDto);
		return ApiResponse.success(MEMBER_PREIN_COMMUNITY_SUCCESS);

	}

	@GetMapping("v1/community/list")
	@Operation(summary = "커뮤니티 목록 조회 API 입니다.",description = "Get Community List")
	public ResponseEntity<ApiResponse<Object>> getCommunityList(Principal principal) {
		return ApiResponse.success(GET_COMMUNITY_LIST_SUCCESS,communityQueryService.getCommunityList());
	}

	@PatchMapping("v2/community/prein")
	@Operation(summary = "사용자 커뮤니티 사전 참여 API 입니다.(반환값 추가 버전)",description = "Member Prein Community")
	public ResponseEntity<ApiResponse<PreinCommunityResponseDto>> preinCommunityVer2(Principal principal, @RequestBody CommunityPreinRequestDto communityPreinRequestDto) {
		Long memberId = MemberUtil.getMemberId(principal);
		return ApiResponse.success(MEMBER_PREIN_COMMUNITY_SUCCESS, communityCommandService.preinCommunityVer2(memberId, communityPreinRequestDto));

	}
}
