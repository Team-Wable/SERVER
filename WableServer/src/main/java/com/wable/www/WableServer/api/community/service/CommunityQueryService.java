package com.wable.www.WableServer.api.community.service;

import com.wable.www.WableServer.api.community.domain.Community;
import com.wable.www.WableServer.api.community.dto.response.GetCommunityListResponseDto;
import com.wable.www.WableServer.api.community.dto.response.GetMemberCommunityResponseDto;
import com.wable.www.WableServer.api.community.repository.CommunityRepository;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityQueryService {
	private final MemberRepository memberRepository;
	private final CommunityRepository communityRepository;

	public GetMemberCommunityResponseDto getMemberCommunity(Long memberId) {
		String memberCommunity = memberRepository.findMemberByIdOrThrow(memberId).getMemberCommunity();
		return GetMemberCommunityResponseDto.of(memberCommunity);
	}

	public List<GetCommunityListResponseDto> getCommunityList() {
		List<Community> communities = communityRepository.findAll();
		return communities.stream()
				.map(community -> GetCommunityListResponseDto.of(community.getCommunityName(), community.calculatePercent()))
				.collect(Collectors.toList());
	}
}
