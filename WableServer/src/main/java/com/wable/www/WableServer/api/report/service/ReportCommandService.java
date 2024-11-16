package com.wable.www.WableServer.api.report.service;

import com.wable.www.WableServer.api.comment.domain.Comment;
import com.wable.www.WableServer.api.comment.repository.CommentRepository;
import com.wable.www.WableServer.api.content.domain.Content;
import com.wable.www.WableServer.api.content.repository.ContentRepository;
import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.api.report.dto.BanRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportCommandService {
	private final CommentRepository commentRepository;
	private final ContentRepository contentRepository;
	private final MemberRepository memberRepository;

	public void blindText(BanRequestDto banRequestDto) {
		Member member = memberRepository.findMemberByIdOrThrow(banRequestDto.memberId());
		if(banRequestDto.triggerType().equals("content")) {
			if(member.getMemberBanCount() == 0) {
				Content content = contentRepository.findContentByIdOrThrow(banRequestDto.triggerId());
				content.blindContent();
				member.banMember();
			}
			else {
				blindAllByMember(member);
				member.banMember();
			}
		}
		else {
			if(member.getMemberBanCount() == 0) {
				Comment comment = commentRepository.findCommentByIdOrThrow(banRequestDto.triggerId());
				comment.blindComment();
				member.banMember();
			}
			else {
				blindAllByMember(member);
				member.banMember();
			}
		}
	}

	private void blindAllByMember(Member member) {
		List<Content> memberContents = contentRepository.findContentByMember(member);
		for (Content content : memberContents) {
			content.blindContent();
		}

		List<Comment> memberComments = commentRepository.findAllByMember(member);
		for (Comment comment : memberComments) {
			comment.blindComment();
		}
	}
}
