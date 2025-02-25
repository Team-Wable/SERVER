package com.wable.www.WableServer.api.community.controller;

import com.wable.www.WableServer.api.community.dto.response.GetMemberCommunityResponseDto;
import com.wable.www.WableServer.api.community.service.CommunityQueryService;
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

import static com.wable.www.WableServer.common.response.SuccessStatus.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/")
@SecurityRequirement(name = "JWT Auth")
@Tag(name="커뮤니티 관련", description = "Community API Document")
public class CommunityController {
	private final CommunityQueryService communityQueryService;

	@GetMapping("v1/community/member")
	@Operation(summary = "사용자 참여 커뮤니티 조회 API 입니다.",description = "Member Get Community")
	public ResponseEntity<ApiResponse<GetMemberCommunityResponseDto>> getMemberCommunity(Principal principal) {
		return ApiResponse.success(GET_MEMBER_COMMUNITY_SUCCESS,communityQueryService.getMemberCommunityResponseDto(MemberUtil.getMemberId(principal)));
	}
}
