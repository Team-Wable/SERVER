package com.wable.www.WableServer.api.community.service;

import com.wable.www.WableServer.api.community.domain.Community;
import com.wable.www.WableServer.api.community.dto.response.GetCommunityListResponseDto;
import com.wable.www.WableServer.api.community.dto.response.GetMemberCommunityResponseDto;
import com.wable.www.WableServer.api.community.repository.CommunityRepository;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.common.util.CommunityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityQueryService {
	private final MemberRepository memberRepository;
	private final CommunityRepository communityRepository;

	private static final Map<String, Integer> TOTAL_MAP = Map.of(
			"T1", 80,
			"DK", 18,
			"GEN", 20,
			"KT", 10,
			"HLE", 22,
			"BFX", 8,
			"DNF", 8,
			"NS", 8,
			"DRX", 18,
			"BRO", 8
	);

	public GetMemberCommunityResponseDto getMemberCommunity(Long memberId) {
		String memberCommunity = memberRepository.findMemberByIdOrThrow(memberId).getMemberCommunity();
		return GetMemberCommunityResponseDto.of(memberCommunity);
	}

	public List<GetCommunityListResponseDto> getCommunityList() {
		List<Community> communities = communityRepository.findAll();
		return communities.stream()
				.map(community -> {
					double percent = CommunityUtil.calculatePercent(community.getCommunityName(), community.getCommunityNumber());
					return GetCommunityListResponseDto.of(community.getCommunityName(), percent);
				})
				.collect(Collectors.toList());
	}
}
