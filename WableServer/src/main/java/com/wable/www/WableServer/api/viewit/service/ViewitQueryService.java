package com.wable.www.WableServer.api.viewit.service;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.api.viewit.domain.Viewit;
import com.wable.www.WableServer.api.viewit.dto.response.ViewitGetAllResponseDto;
import com.wable.www.WableServer.api.viewit.repository.ViewitLikedRepository;
import com.wable.www.WableServer.api.viewit.repository.ViewitRepository;
import com.wable.www.WableServer.common.util.TimeUtilCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ViewitQueryService {
	private final ViewitRepository viewitRepository;
	private final MemberRepository memberRepository;
	private final ViewitLikedRepository viewitLikedRepository;

	public List<ViewitGetAllResponseDto> getViewitAll(Long memberId, Long cursor) {
		PageRequest pageRequest = PageRequest.of(0, 15);
		Member usingMember = memberRepository.findMemberByIdOrThrow(memberId);
		Slice<Viewit> viewitList;

		if(cursor == -1) {
			viewitList = viewitRepository.findTop15ByOrderByCreatedAtDesc(pageRequest);
		} else {
			viewitList = viewitRepository.findViewitsNextPage(cursor, pageRequest);
		}

		return viewitList.stream()
				.map(oneViewit -> ViewitGetAllResponseDto.of(
						memberRepository.findMemberByIdOrThrow(oneViewit.getMemberId()),
						oneViewit,
						TimeUtilCustom.refineTime(oneViewit.getCreatedAt()),
						viewitLikedRepository.existsByViewitIdAndMemberId(oneViewit.getId(), memberId),
						viewitLikedRepository.countByViewitId(oneViewit.getId())))
				.collect(Collectors.toList());
	}
}
