package com.wable.www.WableServer.api.ghost.controller;

import com.wable.www.WableServer.api.ghost.dto.request.GhostClickRequestDto;
import com.wable.www.WableServer.api.ghost.service.GhostCommandService;
import com.wable.www.WableServer.api.member.dto.request.MemberClickGhostRequestDto;
import com.wable.www.WableServer.common.response.ApiResponse;
import com.wable.www.WableServer.common.util.MemberUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static com.wable.www.WableServer.common.response.SuccessStatus.CLICK_MEMBER_GHOST_SUCCESS;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT Auth")
@Tag(name="투명도 관련",description = "Ghost Api Document")
public class GhostController {
    private final GhostCommandService ghostCommandServiceService;

    @PostMapping("ghost")
    @Operation(summary = "투명도 낮추는 API입니다.",description = "MemberGhost")
    public ResponseEntity<ApiResponse<Object>> clickMemberGhost(Principal principal, @RequestBody MemberClickGhostRequestDto memberClickGhostRequestDto) {
        Long memberId = MemberUtil.getMemberId(principal);
        ghostCommandServiceService.clickMemberGhost(memberId, memberClickGhostRequestDto);
        return ApiResponse.success(CLICK_MEMBER_GHOST_SUCCESS);
    }

    @PostMapping("ghost2")
    @Operation(summary = "투명도 낮추는 API입니다.(V2 - 투명도 사유 추가)",description = "MemberGhost")
    public ResponseEntity<ApiResponse<Object>> clickMemberGhost2(Principal principal, @RequestBody @Valid GhostClickRequestDto ghostClickRequestDto) {
        Long memberId = MemberUtil.getMemberId(principal);
        ghostCommandServiceService.clickMemberGhost2(memberId, ghostClickRequestDto);
        return ApiResponse.success(CLICK_MEMBER_GHOST_SUCCESS);
    }
}
