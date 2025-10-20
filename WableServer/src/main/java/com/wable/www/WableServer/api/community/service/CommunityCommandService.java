package com.wable.www.WableServer.api.community.service;

import com.wable.www.WableServer.api.community.domain.Community;
import com.wable.www.WableServer.api.community.dto.request.CommunityPreinRequestDto;
import com.wable.www.WableServer.api.community.dto.response.PreinCommunityResponseDto;
import com.wable.www.WableServer.api.community.repository.CommunityRepository;
import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.common.util.CommunityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityCommandService {
	private final MemberRepository memberRepository;
	private final CommunityRepository communityRepository;

	public void preinCommunity(Long memberId, CommunityPreinRequestDto communityPreinRequestDto) {
		Member member = memberRepository.findMemberByIdOrThrow(memberId);
		member.updateMemberCommunity(communityPreinRequestDto.communityName());

		Community community = communityRepository.getCommunityByCommunityName(communityPreinRequestDto.communityName());
		community.increaseCommunityNumber();
	}

	public PreinCommunityResponseDto preinCommunityVer2(Long memberId, CommunityPreinRequestDto communityPreinRequestDto) {
		Member member = memberRepository.findMemberByIdOrThrow(memberId);
		member.updateMemberCommunity(communityPreinRequestDto.communityName());

		Community community = communityRepository.getCommunityByCommunityName(communityPreinRequestDto.communityName());
		community.increaseCommunityNumber();

		return PreinCommunityResponseDto.of(CommunityUtil.calculatePercent(community.getCommunityName(), community.getCommunityNumber()));
	}
}
