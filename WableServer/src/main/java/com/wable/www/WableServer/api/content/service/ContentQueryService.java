package com.wable.www.WableServer.api.content.service;

import com.wable.www.WableServer.api.comment.repository.CommentRepository;
import com.wable.www.WableServer.api.content.domain.Content;
import com.wable.www.WableServer.api.content.dto.response.*;
import com.wable.www.WableServer.api.content.repository.ContentLikedRepository;
import com.wable.www.WableServer.api.content.repository.ContentRepository;
import com.wable.www.WableServer.api.ghost.repository.GhostRepository;
import com.wable.www.WableServer.api.member.domain.Member;
import com.wable.www.WableServer.api.member.repository.MemberRepository;
import com.wable.www.WableServer.common.util.GhostUtil;
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
public class ContentQueryService {
    private final MemberRepository memberRepository;
    private final ContentRepository contentRepository;
    private final CommentRepository commentRepository;
    private final GhostRepository ghostRepository;
    private final ContentLikedRepository contentLikedRepository;


    public ContentGetDetailsResponseDto getContentDetail(Long memberId, Long contentId) {
        Member member = memberRepository.findMemberByIdOrThrow(memberId);
        Content content = contentRepository.findContentByIdOrThrow(contentId);
        Member writerMember = memberRepository.findMemberByIdOrThrow(content.getMember().getId());
        int writerMemberGhost = GhostUtil.refineGhost(writerMember.getMemberGhost());
        Long writerMemberId = content.getMember().getId();
        boolean isGhost = ghostRepository.existsByGhostTargetMemberIdAndGhostTriggerMemberId(writerMemberId, memberId);
        boolean isLiked = contentLikedRepository.existsByContentAndMember(content,member);
        String time = TimeUtilCustom.refineTime(content.getCreatedAt());
        int likedNumber = contentLikedRepository.countByContent(content);
        int commentNumber = commentRepository.countByContent(content);

        return ContentGetDetailsResponseDto.of(writerMember, writerMemberGhost, content, isGhost, isLiked, time, likedNumber, commentNumber);
    }

    public ContentGetDetailsResponseDtoVer2 getContentDetailVer2(Long memberId, Long contentId) {
        Member member = memberRepository.findMemberByIdOrThrow(memberId);
        Content content = contentRepository.findContentByIdOrThrow(contentId);
        Member writerMember = memberRepository.findMemberByIdOrThrow(content.getMember().getId());
        int writerMemberGhost = GhostUtil.refineGhost(writerMember.getMemberGhost());
        Long writerMemberId = content.getMember().getId();
        boolean isGhost = ghostRepository.existsByGhostTargetMemberIdAndGhostTriggerMemberId(writerMemberId, memberId);
        boolean isLiked = contentLikedRepository.existsByContentAndMember(content,member);
        String time = TimeUtilCustom.refineTime(content.getCreatedAt());
        int likedNumber = contentLikedRepository.countByContent(content);
        int commentNumber = commentRepository.countByContent(content);

        return ContentGetDetailsResponseDtoVer2.of(writerMember, writerMemberGhost, content, isGhost, isLiked, time, likedNumber, commentNumber);
    }

    public ContentGetDetailsResponseDtoVer3 getContentDetailWithImage(Long memberId, Long contentId) {
        Member member = memberRepository.findMemberByIdOrThrow(memberId);
        Content content = contentRepository.findContentByIdOrThrow(contentId);
        Member writerMember = memberRepository.findMemberByIdOrThrow(content.getMember().getId());
        int writerMemberGhost = GhostUtil.refineGhost(writerMember.getMemberGhost());
        Long writerMemberId = content.getMember().getId();
        boolean isGhost = ghostRepository.existsByGhostTargetMemberIdAndGhostTriggerMemberId(writerMemberId, memberId);
        boolean isLiked = contentLikedRepository.existsByContentAndMember(content,member);
        String time = TimeUtilCustom.refineTime(content.getCreatedAt());
        int likedNumber = contentLikedRepository.countByContent(content);
        int commentNumber = commentRepository.countByContent(content);

        return ContentGetDetailsResponseDtoVer3.of(writerMember, writerMemberGhost, content, isGhost, isLiked, time, likedNumber, commentNumber);
    }

    public List<ContentGetAllResponseDto> getContentAll(Long memberId) {    //페이지네이션 적용 후 지우기
        Member usingMember = memberRepository.findMemberByIdOrThrow(memberId);
        List<Content> contents = contentRepository.findAllByOrderByCreatedAtDesc();
        return contents.stream()
                .map(content -> ContentGetAllResponseDto.of(
                        content.getMember(),
                        content,
                        ghostRepository.existsByGhostTargetMemberAndGhostTriggerMember(content.getMember(),usingMember),
                        GhostUtil.refineGhost(content.getMember().getMemberGhost()),
                        contentLikedRepository.existsByContentAndMember(content,usingMember),
                        TimeUtilCustom.refineTime(content.getCreatedAt()),
                        contentLikedRepository.countByContent(content),
                        commentRepository.countByContent(content)))
                .collect(Collectors.toList());
    }

    public List<ContentGetAllResponseDtoVer2> getContentAllPagination(Long memberId, Long cursor) {
        PageRequest pageRequest = PageRequest.of(0, 20);
        Member usingMember = memberRepository.findMemberByIdOrThrow(memberId);
        Slice<Content> contentList;

        if (cursor==-1) {
            contentList = contentRepository.findTop30ByOrderByCreatedAtDesc(pageRequest);
        } else {
            contentList = contentRepository.findContentsNextPage(cursor, pageRequest);
        }

        return contentList.stream()
                .map(oneContent -> ContentGetAllResponseDtoVer2.of(
                        oneContent.getMember(),
                        oneContent,
                        ghostRepository.existsByGhostTargetMemberAndGhostTriggerMember(oneContent.getMember(),usingMember),
                        GhostUtil.refineGhost(oneContent.getMember().getMemberGhost()),
                        contentLikedRepository.existsByContentAndMember(oneContent,usingMember),
                        TimeUtilCustom.refineTime(oneContent.getCreatedAt()),
                        contentLikedRepository.countByContent(oneContent),
                        commentRepository.countByContent(oneContent)))
                .collect(Collectors.toList());
    }

    public List<ContentGetAllResponseDtoVer3> getContentAllWithImage(Long memberId, Long cursor) {
        PageRequest pageRequest = PageRequest.of(0, 20);
        Member usingMember = memberRepository.findMemberByIdOrThrow(memberId);
        Slice<Content> contentList;

        if (cursor==-1) {
            contentList = contentRepository.findTop30ByOrderByCreatedAtDesc(pageRequest);
        } else {
            contentList = contentRepository.findContentsNextPage(cursor, pageRequest);
        }

        return contentList.stream()
                .map(oneContent -> ContentGetAllResponseDtoVer3.of(
                        oneContent.getMember(),
                        oneContent,
                        ghostRepository.existsByGhostTargetMemberAndGhostTriggerMember(oneContent.getMember(),usingMember),
                        GhostUtil.refineGhost(oneContent.getMember().getMemberGhost()),
                        contentLikedRepository.existsByContentAndMember(oneContent,usingMember),
                        TimeUtilCustom.refineTime(oneContent.getCreatedAt()),
                        contentLikedRepository.countByContent(oneContent),
                        commentRepository.countByContent(oneContent)))
                .collect(Collectors.toList());
    }

    public List<ContentGetAllByMemberResponseDto> getContentAllByMember(Long memberId, Long targetMemberId) { //페이지네이션 적용 후 지우기
        Member usingMember = memberRepository.findMemberByIdOrThrow(memberId);
        Member targetMember = memberRepository.findMemberByIdOrThrow(targetMemberId);
        List<Content> contents = contentRepository.findAllByMemberIdOrderByCreatedAtDesc(targetMemberId);
        return contents.stream()
                .map(content -> ContentGetAllByMemberResponseDto.of(
                        targetMember,
                        GhostUtil.refineGhost(content.getMember().getMemberGhost()),
                        content,
                        ghostRepository.existsByGhostTargetMemberAndGhostTriggerMember(targetMember,usingMember),
                        contentLikedRepository.existsByContentAndMember(content,usingMember),
                        TimeUtilCustom.refineTime(content.getCreatedAt()),
                        contentLikedRepository.countByContent(content), commentRepository.countByContent(content)))
                .collect(Collectors.toList());
    }

    public List<ContentGetAllByMemberResponseDto> getContentAllByMemberPagination(Long memberId, Long targetMemberId, Long cursor) {
        Member usingMember = memberRepository.findMemberByIdOrThrow(memberId);
        Member targetMember = memberRepository.findMemberByIdOrThrow(targetMemberId);

        PageRequest pageRequest = PageRequest.of(0, 15);

        Slice<Content> contentList;

        if (cursor==-1) {
            contentList = contentRepository.findContestsTop20ByMemberIdOrderByCreatedAtDesc(targetMemberId, pageRequest);
        } else {
            contentList = contentRepository.findContentsByMemberNextPage(cursor, targetMemberId ,pageRequest);
        }

        return contentList.stream()
                .map(oneContent -> ContentGetAllByMemberResponseDto.of(
                        targetMember,
                        GhostUtil.refineGhost(oneContent.getMember().getMemberGhost()),
                        oneContent,
                        ghostRepository.existsByGhostTargetMemberAndGhostTriggerMember(targetMember,usingMember),
                        contentLikedRepository.existsByContentAndMember(oneContent,usingMember),
                        TimeUtilCustom.refineTime(oneContent.getCreatedAt()),
                        contentLikedRepository.countByContent(oneContent),
                        commentRepository.countByContent(oneContent)))
                .collect(Collectors.toList());
    }

    public List<ContentGetAllByMemberResponseDtoVer2> getContentAllByMemberWithImage(Long memberId, Long targetMemberId, Long cursor) {
        Member usingMember = memberRepository.findMemberByIdOrThrow(memberId);
        Member targetMember = memberRepository.findMemberByIdOrThrow(targetMemberId);

        PageRequest pageRequest = PageRequest.of(0, 15);

        Slice<Content> contentList;

        if (cursor==-1) {
            contentList = contentRepository.findContestsTop20ByMemberIdOrderByCreatedAtDesc(targetMemberId, pageRequest);
        } else {
            contentList = contentRepository.findContentsByMemberNextPage(cursor, targetMemberId ,pageRequest);
        }

        return contentList.stream()
                .map(oneContent -> ContentGetAllByMemberResponseDtoVer2.of(
                        targetMember,
                        GhostUtil.refineGhost(oneContent.getMember().getMemberGhost()),
                        oneContent,
                        ghostRepository.existsByGhostTargetMemberAndGhostTriggerMember(targetMember,usingMember),
                        contentLikedRepository.existsByContentAndMember(oneContent,usingMember),
                        TimeUtilCustom.refineTime(oneContent.getCreatedAt()),
                        contentLikedRepository.countByContent(oneContent),
                        commentRepository.countByContent(oneContent)))
                .collect(Collectors.toList());
    }
}
