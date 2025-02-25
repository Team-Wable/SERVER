package com.wable.www.WableServer.api.community.service;

import com.wable.www.WableServer.api.community.dto.response.GetMemberCommunityResponseDto;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityQueryService {
	private final MemberRepository memberRepository;

	public GetMemberCommunityResponseDto getMemberCommunityResponseDto(Long memberId) {
		String memberCommunity = memberRepository.findMemberByIdOrThrow(memberId).getMemberCommunity();
		return GetMemberCommunityResponseDto.of(memberCommunity);
	}
}
