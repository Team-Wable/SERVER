package com.wable.www.WableServer.api.viewit.service;

import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.api.viewit.domain.Viewit;
import com.wable.www.WableServer.api.viewit.dto.request.ViewitPostRequestDto;
import com.wable.www.WableServer.api.viewit.repository.ViewitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ViewitCommandService {
	private final MemberRepository memberRepository;
	private final ViewitRepository viewitRepository;

	public void postViewit(Long memberId, ViewitPostRequestDto viewitPostRequestDto) {
		Member member = memberRepository.findMemberByIdOrThrow(memberId);

		member.increaseExpPostContent();

		Viewit viewit = viewitRepository.save(Viewit.builder()
				.memberId(memberId)
				.viewitImage(viewitPostRequestDto.viewitImage())
				.viewitTitle(viewitPostRequestDto.viewitTitle())
				.viewitText(viewitPostRequestDto.viewitText())
				.build());
	}
}
