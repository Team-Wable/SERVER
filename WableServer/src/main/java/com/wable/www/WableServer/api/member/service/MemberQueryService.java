package com.wable.www.WableServer.api.member.service;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.dto.response.MemberDetailGetResponseDto;
import com.wable.www.WableServer.api.member.dto.response.MemberGetProfileResponseDto;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.common.exception.BadRequestException;
import com.wable.www.WableServer.common.response.ErrorStatus;
import com.wable.www.WableServer.common.util.GhostUtil;
import com.wable.www.WableServer.common.util.MemberUtil;
import com.wable.www.WableServer.common.util.TimeUtilCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberRepository memberRepository;
    public MemberDetailGetResponseDto getMemberDetail(Long memberId) {
        Member member = memberRepository.findMemberByIdOrThrow(memberId);
        String time = TimeUtilCustom.refineTimeMemberDetail(member.getCreatedAt());
        return MemberDetailGetResponseDto.of(member, time);
    }

    public MemberGetProfileResponseDto getMemberProfile(Long memberId) {
        Member member = memberRepository.findMemberByIdOrThrow(memberId);
        int memberGhost = GhostUtil.refineGhost(member.getMemberGhost());
        int memberLevel = MemberUtil.refineMemberExpToLevel(member.getMemberExp());
        return MemberGetProfileResponseDto.of(member, memberGhost, memberLevel);
    }

    public void checkNicknameValidate(Long memberId, String nickname) {
        String userNickname = memberRepository.findMemberByIdOrThrow(memberId).getNickname();

        if (nickname.equals(userNickname)) {
            return;
        }

        if(memberRepository.existsByNickname(nickname)){
            throw new BadRequestException(ErrorStatus.NICKNAME_VALIDATE_ERROR.getMessage());
        }
    }
}
